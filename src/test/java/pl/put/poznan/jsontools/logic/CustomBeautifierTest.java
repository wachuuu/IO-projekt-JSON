package pl.put.poznan.jsontools.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CustomBeautifierTest {

    private CustomBeautifier custom;
    private Map<String, Object> json;
    private String[] params;
    private String result;

    @BeforeEach
    void setUp(){
        custom = null;
        json = new HashMap<>();
        params = null;
        result = null;
    }

    @Test
    void emptyJsonTest() throws JsonProcessingException {
        params = new String[0];
        ObjectMapper mapper = mock(ObjectMapper.class);
        ObjectWriter writer = mock(ObjectWriter.class);
        when(mapper.writeValueAsString(this.json)).thenReturn("{}");
        custom = new CustomBeautifier(json, params, mapper, writer);
        result = custom.modify();
        assertEquals("{}", result);
    }

    @Test
    void newlinesAndSpacesTest() {
        params = new String[] {"newlines","spaces"};
        json.put("key1","value1");
        json.put("key2",123);
        String expected =
                "{" + System.lineSeparator() +
                "\"key1\" : \"value1\"," + System.lineSeparator() +
                "\"key2\" : 123" + System.lineSeparator() +
                "}";

        custom = new CustomBeautifier(json, params);
        result = custom.modify();
        assertEquals(expected,result);
    }

    @Test
    void arrayNewlinesSpacesTest(){
        params = new String[] {"spaces","newlines"};
        List<String> list = new Vector<>();
        list.add("-g");
        list.add("${file}");
        list.add("ConstructorCpp\\RadaNadzorcza.cpp");
        list.add("ConstructorCpp\\Date.cpp");
        list.add("ConstructorCpp\\Logger.cpp");
        list.add("ConstructorCpp\\Stoper.cpp");
        list.add("-o");
        list.add("${fileDirname}\\${fileBasenameNoExtension}.exe");
        json.put("args",list);
        String expected =
                "{" + System.lineSeparator() +
                "\"args\" : [" + System.lineSeparator() +
                "\"-g\"," + System.lineSeparator() +
                "\"${file}\"," + System.lineSeparator() +
                "\"ConstructorCpp\\\\RadaNadzorcza.cpp\"," + System.lineSeparator() +
                "\"ConstructorCpp\\\\Date.cpp\"," + System.lineSeparator() +
                "\"ConstructorCpp\\\\Logger.cpp\"," + System.lineSeparator() +
                "\"ConstructorCpp\\\\Stoper.cpp\"," + System.lineSeparator() +
                "\"-o\"," + System.lineSeparator() +
                "\"${fileDirname}\\\\${fileBasenameNoExtension}.exe\"" + System.lineSeparator() +
                "]" + System.lineSeparator() +
                "}";
        custom = new CustomBeautifier(json, params);
        result = custom.modify();
        assertEquals(expected,result);
    }

    @Test
    void noParamsTest() throws JsonProcessingException {
        params = new String[0];
        json.put("key1","value1");
        json.put("key2",321);
        List<Object> list = new Vector<>();
        list.add("element1");
        list.add(12345);
        list.add(12.34);
        json.put("key3",list);
        String expected = "{\"key1\":\"value1\",\"key2\":321,\"key3\":[\"element1\",12345,12.34]}";
        ObjectMapper mapper = mock(ObjectMapper.class);
        ObjectWriter writer = mock(ObjectWriter.class);
        when(mapper.writeValueAsString(this.json)).thenReturn(expected);
        custom = new CustomBeautifier(json, params, mapper, writer);
        result = custom.modify();
        assertEquals(expected,result);
    }

    @Test
    void nestedJsonAllParamsTest() throws JsonProcessingException {
        params = new String[] {"spaces","newlines","tabs"};
        json.put("bus_id",1);
        json.put("number_plate","PO 12345");
        json.put("purchase_date","2019-04-13");
        json.put("service_date","2021-12-04");
        json.put("monthly_maintenance_cost",1300);
        json.put("cost",200000);
        Map<String,Object> nested1 = new HashMap<>();
        nested1.put("model_id",1);
        nested1.put("model_name","AR-150");
        nested1.put("year_of_production",2010);
        nested1.put("number_of_seats",50);
        Map<String,Object> nested2 = new HashMap<>();
        nested2.put("brand_id",1);
        nested2.put("name","Solaris");
        nested1.put("brand",nested2);
        json.put("bus_model",nested1);
        String expected =
                "{" + System.lineSeparator() +
                "  \"bus_model\" : {" + System.lineSeparator() +
                "    \"model_name\" : \"AR-150\"," + System.lineSeparator() +
                "    \"model_id\" : 1," + System.lineSeparator() +
                "    \"year_of_production\" : 2010," + System.lineSeparator() +
                "    \"brand\" : {" + System.lineSeparator() +
                "      \"name\" : \"Solaris\"," + System.lineSeparator() +
                "      \"brand_id\" : 1" + System.lineSeparator() +
                "    }," + System.lineSeparator() +
                "    \"number_of_seats\" : 50" + System.lineSeparator() +
                "  }," + System.lineSeparator() +
                "  \"cost\" : 200000," + System.lineSeparator() +
                "  \"service_date\" : \"2021-12-04\"," + System.lineSeparator() +
                "  \"number_plate\" : \"PO 12345\"," + System.lineSeparator() +
                "  \"bus_id\" : 1," + System.lineSeparator() +
                "  \"purchase_date\" : \"2019-04-13\"," + System.lineSeparator() +
                "  \"monthly_maintenance_cost\" : 1300" + System.lineSeparator() +
                "}";

        ObjectMapper mapper = mock(ObjectMapper.class);
        ObjectWriter writer = mock(ObjectWriter.class);
        when(writer.writeValueAsString(this.json)).thenReturn(expected);
        custom = new CustomBeautifier(json, params, mapper, writer);
        result = custom.modify();
        assertEquals(expected,result);
    }
}
