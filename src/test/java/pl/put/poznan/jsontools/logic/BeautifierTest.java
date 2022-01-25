package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BeautifierTest {

    private Beautifier beautifier;
    private Map<String,Object> json;
    private String result;

    @BeforeEach
    void setUp(){
        beautifier = null;
        json = new HashMap<>();
        result = null;
    }

    @Test
    void modifyEmptyJsonTest() throws JsonProcessingException {
        String expected = "{ }";
        ObjectWriter writer = mock(ObjectWriter.class);
        when(writer.writeValueAsString(json)).thenReturn(expected);
        beautifier = new Beautifier(json,writer);
        result = beautifier.modify();
        assertEquals(expected, result);
    }

    @Test
    void modifySimpleJsonTest() throws JsonProcessingException {
        json.put("key1","value1");
        json.put("key2",123);
        String expected =
                "{" + System.lineSeparator() +
                        "  \"key1\" : \"value1\"," + System.lineSeparator() +
                        "  \"key2\" : 123" + System.lineSeparator() +
                        "}";

        ObjectWriter writer = mock(ObjectWriter.class);
        when(writer.writeValueAsString(json)).thenReturn(expected);
        beautifier = new Beautifier(json,writer);
        result = beautifier.modify();
        assertEquals(expected,result);
    }

    @Test
    void modifyWithArrayTest(){
        json.put("key1","value1");
        json.put("key2",321);
        List<Object> list = new Vector<>();
        list.add("element1");
        list.add(12345);
        list.add(12.34);
        json.put("key3",list);
        String expected =
                "{" + System.lineSeparator() +
                        "  \"key1\" : \"value1\"," + System.lineSeparator() +
                        "  \"key2\" : 321," + System.lineSeparator() +
                        "  \"key3\" : [ \"element1\", 12345, 12.34 ]" + System.lineSeparator() +
                        "}";

        beautifier = new Beautifier(json);
        result = beautifier.modify();
        assertEquals(expected,result);
    }

    @Test
    void modifyWithArrayOfObjectsTest() throws JsonProcessingException {
        json.put("id","b1");
        json.put("name","building");
        List<Map<String,Object>> floors = new Vector<>();
        Map<String,Object> nestedMap = new HashMap<>();
        nestedMap.put("id","f1");
        nestedMap.put("name","first floor");
        Map<String,Object> room = new HashMap<>();
        room.put("id","f1r1");
        room.put("name","first room");
        room.put("area",100);
        room.put("cube",123);
        room.put("heating",1.64);
        room.put("light",200);
        List<Map<String,Object>> rooms = new Vector<>();
        rooms.add(room);
        rooms.add(room);
        rooms.add(room);
        nestedMap.put("rooms",rooms);
        floors.add(nestedMap);
        json.put("floors",floors);

        String expected =
                "{" + System.lineSeparator() +
                        "  \"id\" : \"b1\"," + System.lineSeparator() +
                        "  \"name\" : \"building\"," + System.lineSeparator() +
                        "  \"floors\" : [ {" + System.lineSeparator() +
                        "    \"id\" : \"f1\"," + System.lineSeparator() +
                        "    \"name\" : \"first floor\"," + System.lineSeparator() +
                        "    \"rooms\" : [ {" + System.lineSeparator() +
                        "      \"id\" : \"f1r1\"," + System.lineSeparator() +
                        "      \"name\" : \"first room\"," + System.lineSeparator() +
                        "      \"area\" : 100," + System.lineSeparator() +
                        "      \"cube\" : 123," + System.lineSeparator() +
                        "      \"heating\" : 1.64," + System.lineSeparator() +
                        "      \"light\" : 200" + System.lineSeparator() +
                        "    }, {" + System.lineSeparator() +
                        "      \"id\" : \"f1r1\"," + System.lineSeparator() +
                        "      \"name\" : \"first room\"," + System.lineSeparator() +
                        "      \"area\" : 100," + System.lineSeparator() +
                        "      \"cube\" : 123," + System.lineSeparator() +
                        "      \"heating\" : 1.64," + System.lineSeparator() +
                        "      \"light\" : 200" + System.lineSeparator() +
                        "    } ]" + System.lineSeparator() +
                        "  } ]" + System.lineSeparator() +
                        "}";
        ObjectWriter writer = mock(ObjectWriter.class);
        when(writer.writeValueAsString(json)).thenReturn(expected);
        beautifier = new Beautifier(json,writer);
        result = beautifier.modify();
        assertEquals(expected,result);
    }

    @Test
    void nestedJsonTest() {
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

        beautifier = new Beautifier(json);
        result = beautifier.modify();
        assertEquals(expected,result);
    }
}
