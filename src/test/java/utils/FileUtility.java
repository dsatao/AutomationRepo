package utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.Logger;
import org.testng.SkipException;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import io.cucumber.datatable.DataTable;

/*
 * @author : Jayesh.Hinge
 */

public class FileUtility {

	public static String updateValuesInFile(String filepath, Map<String, String> requestBody, Logger log) {
		String actual = null;
		try {
			Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
			File json = new File(filepath);
			DocumentContext parsed = JsonPath.using(conf).parse(json);
			if (requestBody.size() < 1) {
				throw new InterruptedException("Please set request body first as key($.key1) and value format");
			}

			System.out.println("Request Body:" + requestBody);
			for (Entry<String, String> entry : requestBody.entrySet()) {
				parsed.set((String) entry.getKey(), entry.getValue());
			}
			actual = parsed.jsonString();
			if (actual.contains("#$")) {
				String s2 = actual.substring(actual.indexOf("#$")).substring(0,
						actual.substring(actual.indexOf("#$")).indexOf("\""));
				log.error("Please set request body for key " + s2.substring(2) + " and value");
				throw new SkipException("Please set request body for key [" + s2.substring(2) + "]");
			}
		} catch (IOException io) {
			log.error("Exception while reading file " + filepath + " Message  " + io.getMessage());
		} catch (InterruptedException ie) {
			log.error("Message  " + ie.getMessage());
		}
		return actual;
	}

	public static String loadFileIntoString(String filepath, Logger log) {
		String actual = null;
		try {
			Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
			File json = new File(filepath);
			DocumentContext parsed = JsonPath.using(conf).parse(json);
			actual = parsed.jsonString();
		} catch (IOException io) {
			log.error("Exception while reading file " + filepath + " Message  " + io.getMessage());
		}
		return actual;
	}

	public static String replaceValuesInFile(String filepath, Map<String, String> requestBody, Logger log) {
		String actual = null;
		try {
			Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
			File json = new File(filepath);
			DocumentContext parsed = JsonPath.using(conf).parse(json);
			if (requestBody.size() < 1) {
				log.error("Please set request body first as key and value format");
				throw new SkipException("Please set request body first as key and value format");
			}
			actual = parsed.jsonString();
			while (actual.contains("#$")) {
				String s2 = actual.substring(actual.indexOf("#$")).substring(0,
						actual.substring(actual.indexOf("#$")).indexOf("\""));
				if (requestBody.containsKey(s2.substring(2))) {
					String value = (String) requestBody.get(s2.substring(2));
					log.info("Key " + s2.substring(2) + "Value " + value);
					actual = actual.replace(s2, value);
				} else {
					log.error("Please set request header for key [" + s2.substring(2) + "]");
					throw new SkipException("Please set request body for key [" + s2.substring(2) + "]");
				}
			}
		} catch (IOException io) {
			log.error("Exception while reading file " + filepath + " Message  " + io.getMessage());
		}
		return actual;
	}

	public static Map<String, String> readDataTableAndReturnMap(DataTable table, Logger log,
			Map<String, String> requestBody) {
		int numberOfCells = table.cells().size();
		// ConcurrentHashMap<String, String> body = new ConcurrentHashMap<>();
		List<Map<String, String>> rows = table.asMaps(String.class, String.class);
		if (numberOfCells < 1 || rows.size() < 1) {
			log.error("Please add data table in row column format(first row should be header)");
			throw new SkipException("Please add data table in row column format(first row should be header)");
		}
		if (rows.size() > 1) {
			log.error("Please add data table with only one row of data(excluding header)");
			throw new SkipException("Please add data table with only one row of data(excluding header)");
		}
		for (Map<String, String> columns : rows) {
			for (int i = 0; i < columns.size(); i++) {
				requestBody.put((String) columns.keySet().toArray()[i], (String) columns.values().toArray()[i]);
			}
		}
		// requestBody = body;
		return requestBody;
	}

}
