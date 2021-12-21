package pl.put.poznan.jsontools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Beautifier takes a JSON file and returns a string in a chosen format.
 * A user can choose whether he wants to use tabs, newlines or spaces.
 *
 * @param body - a JSON body on which the operations are to be performed
 * @param String[] params - a string lsit of parameters (tabs, newlines, spaces)
 */

public class CustomBeautifier extends JSONModifier {

    public CustomBeautifier(Map<String, Object> body, String[] params) {
        super(body, params);
    }

    /**
     * modify takes a JSON body and returns a custom version on it depending on which parameters were given in the constructor
     *
     * @return String beautified - a prettier, more readable text version of the JSON with
     *                             proper whitespaces and indentation
     */

    public String modify() {

        Boolean tabs = false, newlines = false, spaces = false;

        ObjectMapper mapper = new ObjectMapper();
        String result = null;

        for (String i : params){
            if (i.equals("tabs")){
                tabs = true;
                newlines = true; // "tabs" without "newlines" make no sense so we set them automatically
            }
            if (i.equals("newlines"))
                newlines = true;
            if (i.equals("spaces"))
                spaces = true;
        }

        if (tabs){
            DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
            if (spaces){
                try {
                    result = mapper.writer(printer).writeValueAsString(body);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    result = mapper.writer(printer.withoutSpacesInObjectEntries()).writeValueAsString(body);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!tabs){
            DefaultPrettyPrinter.Indenter indenter =  new DefaultIndenter("", DefaultIndenter.SYS_LF);
            DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
            printer.indentObjectsWith(indenter);
            printer.indentArraysWith(indenter);
            if (!newlines){
                if (spaces) {
                    try {
                        MinimalPrettyPrinter minimalPrettyPrinter = new MinimalPrettyPrinter();
                        result = mapper.writer(minimalPrettyPrinter).writeValueAsString(body);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        result = mapper.writeValueAsString(body);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                if (spaces) {
                    try {
                        result = mapper.writer(printer).writeValueAsString(body);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        result = mapper.writer(printer.withoutSpacesInObjectEntries()).writeValueAsString(body);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
