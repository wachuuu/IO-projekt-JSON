package pl.put.poznan.jsontools.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.jsontools.logic.*;

import java.util.Arrays;
import java.util.Map;


@RestController
@RequestMapping("/json")
public class JSONToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    public JSONTool modifier;

    @GetMapping("/minify")
    public ResponseEntity<Map<String, Object>> minifyJSON(@RequestBody Map<String, Object> json) {
        logger.debug("Minified: " + json.toString());

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/beautify")
    public ResponseEntity<Map<String, Object>> beautifyJSON(@RequestBody Map<String, Object> json) {
        modifier = new Beautifier(json.toString());
        modifier.modify();
        logger.debug("Beautified: " + json.toString());

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/custom/beautify")
    public ResponseEntity<Map<String, Object>> customBeautifyJSON(@RequestBody Map<String, Object> json,
                                                                  @RequestParam(value = "transformations", defaultValue = "tabs, newlines, spaces") String[] transformations) {

        modifier = new CustomBeautifier(json.toString(), transformations);
        modifier.modify();

        logger.debug("Beautified with params: " + json.toString());
        logger.debug("Params: " + Arrays.toString(transformations));

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
