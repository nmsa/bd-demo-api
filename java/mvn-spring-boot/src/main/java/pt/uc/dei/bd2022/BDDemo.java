/** **
 * =============================================
 * ============== Bases de Dados ===============
 * ============== LEI  2021/2022 ===============
 * =============================================
 * =================== Demo ====================
 * =============================================
 * =============================================
 * === Department of Informatics Engineering ===
 * =========== University of Coimbra ===========
 * =============================================
 * <p>
 *
 * Authors:
 *   Nuno Antunes <nmsa@dei.uc.pt>
 *   BD 2022 Team - https://dei.uc.pt/lei/
 */
package pt.uc.dei.bd2022;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BDDemo {

    public enum StatusCode {

        SUCCESS ("success", 200),
        API_ERROR ("api_error", 400),
        INTERNAL_ERROR ("internal_error", 500);
    
        private final String description; 
        private final int code; 
        
        StatusCode(String description, int code) {
            this.description = description;
            this.code = code;
        }
        
        private String description() { 
            return description; 
        }

        private int code() { 
            return code; 
        }
    }


    private static final Logger logger = LoggerFactory.getLogger(BDDemo.class);

    @GetMapping("/")
    public String landing() {
        return "Hello World (Java)!  <br/>\n"
                + "<br/>\n"
                + "Check the sources for instructions on how to use the endpoints!<br/>\n"
                + "<br/>\n"
                + "BD 2022 Team<br/>\n"
                + "<br/>";
    }

    /**
     * Demo GET
     *
     *
     * Obtain all departments, in JSON format
     *
     * To use it, access: <br>
     * http://localhost:8080/departments/
     *
     *
     * @return
     */
    @GetMapping(value = "/departments/", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getAllDepartments() {
        logger.info("###              DEMO: GET /departments              ###");
        Connection conn = RestServiceApplication.getConnection();
        Map<String, Object> returnData = new HashMap<String, Object>();
        List<Map<String, Object>> results = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rows = stmt.executeQuery("SELECT ndep, nome, local FROM dep");
            logger.debug("---- departments  ----");
            while (rows.next()) {
                Map<String, Object> content = new HashMap<>();
                logger.debug("'ndep': {}, 'nome': {}, 'localidade': {}",
                        rows.getInt("ndep"), rows.getString("nome"), rows.getString("local")
                );
                content.put("ndep", rows.getInt("ndep"));
                content.put("nome", rows.getString("nome"));
                content.put("localidade", rows.getString("local"));
                results.add(content);
            }

            returnData.put("status", StatusCode.SUCCESS.code());
            returnData.put("results", results);

        } catch (SQLException ex) {
            logger.error("Error in DB", ex);
            returnData.put("status", StatusCode.INTERNAL_ERROR.code());
            returnData.put("errors", ex.getMessage());
        }
        return returnData;
    }

    /**
     * Demo GET
     *
     *
     * Obtain department with {@code ndep}
     *
     * To use it, access: <br>
     * http://localhost:8080/departments/
     *
     *
     * @param ndep id of the department to be selected
     * @return data of the department
     */
    @GetMapping(value = "/departments/{ndep}", produces = "application/json")
    @ResponseBody
    public Map<String, Object> getDepartment(
            @PathVariable("ndep") int ndep
    ) {
        logger.info("###              DEMO: GET /departments              ###");
        Connection conn = RestServiceApplication.getConnection();

        Map<String, Object> returnData = new HashMap<String, Object>();

        Map<String, Object> content = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT ndep, nome, local FROM dep WHERE ndep = ?")) {
            ps.setInt(1, ndep);
            ResultSet rows = ps.executeQuery();
            logger.debug("---- selected department  ----");
            if (rows.next()) {
                logger.debug("'ndep': {}, 'nome': {}, 'localidade': {}", rows.getInt("ndep"), rows.getString("nome"), rows.getString("local"));
                content.put("ndep", rows.getInt("ndep"));
                content.put("nome", rows.getString("nome"));
                content.put("localidade", rows.getString("local"));
            }

            returnData.put("status", StatusCode.SUCCESS.code());
            returnData.put("results", content);

        } catch (SQLException ex) {
            logger.error("Error in DB", ex);
            returnData.put("status", StatusCode.INTERNAL_ERROR.code());
            returnData.put("errors", ex.getMessage());
        }
        return returnData;
    }

    /**
     * Demo POST
     *
     *
     * Add a new department in a JSON payload
     *
     * To use it, you need to use postman or curl:
     *
     * {@code curl -X POST http://localhost:8080/departments/ -H "Content-Type: application/json" -d
     * '{"localidade": "Polo II", "ndep": 69, "nome": "Seguranca"}'}
     *
     *
     */
    @PostMapping(value = "/departments/", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> createDepartment(
            @RequestBody Map<String, Object> payload
    ) {

        logger.info("###              DEMO: POST /departments              ###");
        Connection conn = RestServiceApplication.getConnection();

        logger.debug("---- new department  ----");
        logger.debug("payload: {}", payload);

        Map<String, Object> returnData = new HashMap<String, Object>();

        // validate all the required inputs and types, e.g.,
        if (!payload.containsKey("localidade")) {
            logger.warn("localidade are required to update");
            returnData.put("status", StatusCode.API_ERROR.code());
            returnData.put("errors", "localidade are required to update");
            return returnData;
        }

        try (PreparedStatement ps = conn.prepareStatement(""
                + "INSERT INTO dep (ndep, nome, local) "
                + "         VALUES (  ? ,   ? ,    ? )")) {
            ps.setInt(1, (int) payload.get("ndep"));
            ps.setString(2, (String) payload.get("nome"));
            ps.setString(3, (String) payload.get("localidade"));
            int affectedRows = ps.executeUpdate();
            conn.commit();

            returnData.put("status", StatusCode.SUCCESS.code());
            returnData.put("results", "Department inserted successfully");

        } catch (SQLException ex) {
            logger.error("Error in DB", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                logger.warn("Couldn't rollback", ex);
            }

            returnData.put("status", StatusCode.INTERNAL_ERROR.code());
            returnData.put("errors", ex.getMessage());

        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.error("Error in DB", ex);
            }
        }
        return returnData;
    }

    /**
     * Demo PUT
     *
     *
     * Update a department based on the a JSON payload
     *
     * o use it, you need to use postman or curl:
     *
     * {@code curl -X PUT http://localhost:8080/departments/ -H "Content-Type: application/json" -d '{"ndep": 69, "localidade": "Porto"}'}
     *
     */
    @PutMapping(value = "/departments/{ndep}", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> updateDepartment(
            @PathVariable("ndep") int ndep,
            @RequestBody Map<String, Object> payload
    ) {

        logger.info("###              DEMO: PUT /departments               ###");

        Map<String, Object> returnData = new HashMap<String, Object>();

        // validate all the required inputs and types, e.g.,
        if (!payload.containsKey("localidade")) {
            logger.warn("localidade are required to update");
            returnData.put("status", StatusCode.API_ERROR.code());
            returnData.put("errors", "localidade are required to update");
            return returnData;
        }

        logger.info("---- update department  ----");
        logger.debug("content: {}", payload);
        Connection conn = RestServiceApplication.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(""
                + "UPDATE dep"
                + "   SET local = ? "
                + " WHERE ndep = ?")) {

            ps.setString(1, (String) payload.get("localidade"));
            ps.setInt(2, ndep);

            int affectedRows = ps.executeUpdate();
            conn.commit();

            returnData.put("status", StatusCode.SUCCESS.code());
            returnData.put("results", "Department updated successfully");

        } catch (SQLException ex) {
            logger.error("Error in DB", ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                logger.warn("Couldn't rollback", ex);
            }

            returnData.put("status", StatusCode.INTERNAL_ERROR.code());
            returnData.put("errors", ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.error("Error in DB", ex);
            }
        }
        return returnData;
    }
}
