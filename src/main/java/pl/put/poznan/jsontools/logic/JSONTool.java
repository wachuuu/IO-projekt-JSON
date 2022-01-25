package pl.put.poznan.jsontools.logic;

/**
 * JSONTool is an interface upon which all further JSON modyfing classes are built
 */

public interface JSONTool {

    /**
     * modify is to take a JSON file and return it in a modified format
     *
     * @return a String containing JSON after the modification
     */
    String modify();
}

