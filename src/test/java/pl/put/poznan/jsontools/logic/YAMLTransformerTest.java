package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class YAMLTransformerTest {

    private Map<String, Object> json;

    @BeforeEach
    void setup(){
        this.json = new HashMap<>();
    }

    @Test
    void modifyEmptyJson() throws JsonProcessingException {
        YAMLMapper yamlMapper = mock(YAMLMapper.class);
        when(yamlMapper.writeValueAsString(this.json)).thenReturn("<?xml version='1.1' encoding='UTF-8'?>\n"
                + "<LinkedHashMap/>");

        YAMLTransformer yamlTransformer = new YAMLTransformer(this.json, yamlMapper);

        String yaml = yamlTransformer.modify();
        assertEquals("<?xml version='1.1' encoding='UTF-8'?>\n<LinkedHashMap/>", yaml);
    }

    @Test
    void modifyNestedJson(){
        Map<String, Object> subObj = new HashMap<>();
        subObj.put("name2", "value2");
        this.json.put("name1", subObj);

        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        YAMLTransformer yamlTransformer = new YAMLTransformer(this.json, yamlMapper);
        String yaml = yamlTransformer.modify();

        String expected = "---\nname1:\n  name2: \"value2\"\n";

        assertEquals(expected, yaml);
    }

    @Test
    void modifyOnePropertyJson() throws JsonProcessingException {
        this.json.put("text", "xxxx");

        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        YAMLTransformer yamlTransformer = new YAMLTransformer(this.json, yamlMapper);
        String yaml = yamlTransformer.modify();

        String expected = "---\ntext: \"xxxx\"\n";

        assertEquals(expected, yaml);
    }

    @Test
    void modifyManyPropertiesJson() throws JsonProcessingException {
        this.json.put("text", "xxxx");
        this.json.put("text2", "xxxx");
        this.json.put("text3", "xxxx");

        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        YAMLTransformer yamlTransformer = new YAMLTransformer(this.json, yamlMapper);
        String yaml = yamlTransformer.modify();

        String expected = "---\ntext3: \"xxxx\"\n" +
                "text2: \"xxxx\"\n" +
                "text: \"xxxx\"\n";

        assertEquals(expected, yaml);
    }

    @Test
    void modifyManyPropertiesNestedJson() throws JsonProcessingException{
        Map<String, Object> subObj = new HashMap<>();
        subObj.put("text3", "value3");
        this.json.put("text1", "value1");
        this.json.put("text2", subObj);

        String expected = "---/n" +
                "text1: \"value1\"\n" +
                "text2: \"text3: \"value3\"\"\n";

        YAMLMapper yamlMapper = mock(YAMLMapper.class);
        when(yamlMapper.writeValueAsString(this.json)).thenReturn(expected);

        YAMLTransformer yamlTransformer = new YAMLTransformer(this.json, yamlMapper);

        String yaml = yamlTransformer.modify();
        assertEquals(expected, yaml);
    }
}