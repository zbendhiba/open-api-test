package org.example;

import java.io.IOException;
import java.util.Optional;

import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.core.models.Document;
import io.apicurio.datamodels.openapi.models.OasDocument;
import io.apicurio.datamodels.openapi.models.OasOperation;
import io.apicurio.datamodels.openapi.models.OasPathItem;
import io.apicurio.datamodels.openapi.models.OasPaths;

public class Main {
    public final static String OPENAPI_JSON_SCHEMA = "{\n" +
            "  \"openapi\" : \"3.0.3\",\n" +
            "  \"info\" : {\n" +
            "    \"title\" : \"openapi-swaggerui-quickstart API\",\n" +
            "    \"version\" : \"1.0.0-SNAPSHOT\"\n" +
            "  },\n" +
            "  \"paths\" : {\n" +
            "    \"/fruits\" : {\n" +
            "      \"get\" : {\n" +
            "        \"tags\" : [ \"Fruit Resource\" ],\n" +
            "        \"responses\" : {\n" +
            "          \"200\" : {\n" +
            "            \"description\" : \"OK\",\n" +
            "            \"content\" : {\n" +
            "              \"application/json\" : {\n" +
            "                \"schema\" : {\n" +
            "                  \"uniqueItems\" : true,\n" +
            "                  \"type\" : \"array\",\n" +
            "                  \"items\" : {\n" +
            "                    \"$ref\" : \"#/components/schemas/Fruit\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"post\" : {\n" +
            "        \"tags\" : [ \"Fruit Resource\" ],\n" +
            "        \"requestBody\" : {\n" +
            "          \"content\" : {\n" +
            "            \"application/json\" : {\n" +
            "              \"schema\" : {\n" +
            "                \"$ref\" : \"#/components/schemas/Fruit\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"responses\" : {\n" +
            "          \"200\" : {\n" +
            "            \"description\" : \"OK\",\n" +
            "            \"content\" : {\n" +
            "              \"application/json\" : {\n" +
            "                \"schema\" : {\n" +
            "                  \"uniqueItems\" : true,\n" +
            "                  \"type\" : \"array\",\n" +
            "                  \"items\" : {\n" +
            "                    \"$ref\" : \"#/components/schemas/Fruit\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"delete\" : {\n" +
            "        \"tags\" : [ \"Fruit Resource\" ],\n" +
            "        \"requestBody\" : {\n" +
            "          \"content\" : {\n" +
            "            \"application/json\" : {\n" +
            "              \"schema\" : {\n" +
            "                \"$ref\" : \"#/components/schemas/Fruit\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"responses\" : {\n" +
            "          \"200\" : {\n" +
            "            \"description\" : \"OK\",\n" +
            "            \"content\" : {\n" +
            "              \"application/json\" : {\n" +
            "                \"schema\" : {\n" +
            "                  \"uniqueItems\" : true,\n" +
            "                  \"type\" : \"array\",\n" +
            "                  \"items\" : {\n" +
            "                    \"$ref\" : \"#/components/schemas/Fruit\"\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"components\" : {\n" +
            "    \"schemas\" : {\n" +
            "      \"Fruit\" : {\n" +
            "        \"type\" : \"object\",\n" +
            "        \"properties\" : {\n" +
            "          \"name\" : {\n" +
            "            \"type\" : \"string\",\n" +
            "            \"nullable\": false\n" +
            "          },\n" +
            "          \"description\" : {\n" +
            "            \"type\" : \"string\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    public static void main(String[] args) throws IOException {
        Document openapiDoc = Library.readDocumentFromJSONString(OPENAPI_JSON_SCHEMA);
        // Get to the desired path
        var pathId = "/fruits";
        final OasPaths paths = ((OasDocument) openapiDoc).paths;
        Optional<OasPathItem> desiredPath = paths.getItems().stream().filter(oasPathItem -> pathId.equals(oasPathItem.getPath())).findFirst();
        if (desiredPath.isPresent()) {
            OasOperation desiredOperation = desiredPath.get().post;
            //TODO how to access schema or reference from here ?
        }
    }
}
