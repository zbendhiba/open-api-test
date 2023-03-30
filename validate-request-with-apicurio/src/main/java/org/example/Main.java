package org.example;

import java.io.IOException;

import io.apicurio.datamodels.Library;
import io.apicurio.datamodels.core.models.Document;
import io.apicurio.datamodels.core.util.ReferenceUtil;
import io.apicurio.datamodels.openapi.models.OasDocument;
import io.apicurio.datamodels.openapi.models.OasPaths;
import io.apicurio.datamodels.openapi.v3.models.Oas30Operation;
import io.apicurio.datamodels.openapi.v3.models.Oas30Response;
import io.apicurio.datamodels.openapi.v3.models.Oas30Schema;

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

        // We know we want an operation in the '/fruits' path, so get it directly.
        final OasPaths paths = ((OasDocument) openapiDoc).paths;
        var path = paths.getPathItem(pathId);

        // We care about the POST operation.
        Oas30Operation operation = (Oas30Operation) path.post;

        // We want to validate the *input* message
        var request = operation.requestBody;

        // Get the content by its media-type - usually this will be "application/json" but
        // we could be something else.  We would need handling here to check for other types.
        var mediaType = request.content.get("application/json");

        // Get the defined schema for that media type.
        var inputSchema = mediaType.schema;

        // The schema *might* have a $ref that points to something else.  Let Apicurio
        // resolve that for us.  Note: by default this will work for internal references only.
        // It CAN work for external references but that requires additional configuration.
        inputSchema = (Oas30Schema) ReferenceUtil.resolveNodeRef(inputSchema);

        // At this point we have the schema we want to use for validation.
        System.out.println("---------- Schema to use to validate the operation INPUT");
        System.out.println(Library.writeNode(inputSchema));
        System.out.println("----------");

        // Now lets get the OUTPUT schema (not sure if this is needed).

        // Get the 200 response.  Real logic would need to be more robust, since the operation
        // might have some other 2xx response indicating success.
        Oas30Response response = (Oas30Response) operation.responses.getResponse("200");
        var outputSchema = ReferenceUtil.resolveNodeRef(response.content.get("application/json").schema);

        // At this point we have the schema we want to use for validation of the OUTPUT.
        System.out.println("---------- Schema to use to validate the operation OUTPUT");
        System.out.println(Library.writeNode(outputSchema));
        System.out.println("----------");

        // Note about the output schema:  it has a $ref in the "items".  I'm not sure how the
        // JSON validation library handles reference resolution.  You might need to provide a
        // custom resolver to the JSON validation library (if it supports that) so that you
        // can resolve any $refs the schema might have.  Implementation of that would be
        // possible using the ReferenceUtil class from above.  For example:
        var resolvedSchema = ReferenceUtil.resolveRef("#/components/schemas/Fruit", openapiDoc);
        System.out.println("---------- Resolved Items schema");
        System.out.println(Library.writeNode(resolvedSchema));
        System.out.println("----------");

    }
}
