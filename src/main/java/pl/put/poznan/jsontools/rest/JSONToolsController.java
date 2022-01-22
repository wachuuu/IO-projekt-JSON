package pl.put.poznan.jsontools.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
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
    public ResponseEntity<String> minifyJSON(@RequestBody Map<String, Object> json) {
        modifier = new Minifier(json);
        String output = modifier.modify();

        logger.debug("Minified: " + json.toString());

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/beautify")
    public ResponseEntity<String> beautifyJSON(@RequestBody Map<String, Object> json) {
        modifier = new Beautifier(json);
        String output = modifier.modify();

        logger.debug("Beautified: " + output);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/custom/beautify")
    public ResponseEntity<String> customBeautifyJSON(@RequestBody Map<String, Object> json,
                                                     @RequestParam String[] transformations) {
        modifier = new CustomBeautifier(json, transformations);
        String output = modifier.modify();

        logger.debug("Beautified with params: " + output);
        logger.debug("Params: " + Arrays.toString(transformations));

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/to-yaml")
    public ResponseEntity<String> jsonToYAML(@RequestBody Map<String, Object> json) {

        String output = json.toString();
        logger.debug("Transformed to YAML: " + output);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/to-xml")
    public ResponseEntity<String> jsonToXML(@RequestBody Map<String, Object> json) {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);

        XMLTransformer transformer = new XMLTransformer(json, xmlMapper);
        String output = transformer.modify();

        logger.debug("Transformed to XML: " + output);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/to-csv")
    public ResponseEntity<String> jsonToCSV(@RequestBody Map<String, Object> json) {

        String output = json.toString();
        logger.debug("Transformed to CSV: " + output);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
