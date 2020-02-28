package com.sifast.convertToJava.tn.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EntityGeneratorService {
	
	
	public void generateEntityDynamic(String packageName, String className, String projectName, String fieldName,
			String fieldType) {
		if (!"undefined".equals(className)) {
			try {
				System.out.println("class name = " + className);
				String newPackage = packageName.replace(".", "/");
				FileWriter myWriter = new FileWriter("/Users/qc/Desktop/generator-test/" + projectName
						+ "/src/main/java/" + newPackage + "/model/" + className + ".java");
				myWriter.write("package " + packageName + ".model" + ";\n");
				myWriter.write("import javax.persistence.*;\n");
				myWriter.write("import java.io.Serializable;\n \n");
				myWriter.write("@Entity\n");
				myWriter.write("public class " + className + " implements Serializable {\n");
				myWriter.write("	private static final long serialVersionUID = 1L;\n");
				myWriter.write("	private " + fieldType + " " + fieldName + ";\n");
				myWriter.write("\n");
				myWriter.write("}");
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			System.out.println("class name null");
		}

	}

	public void replaceString(String packageName, String projectName) throws IOException {
		String newPackage = packageName.replace(".", "/");
		String pathProject = "/Users/qc/Desktop/generator-test/" + projectName + "/src/main/java/" + newPackage
				+ "/DemoApplication.java";
		List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(pathProject), StandardCharsets.UTF_8));

		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).equals("package com.example." + projectName + ";")) {
				fileContent.set(i, "package " + packageName + ";");
				break;
			}
		}

		Files.write(Paths.get(pathProject), fileContent, StandardCharsets.UTF_8);
	}


	public void generateEntityDynamicWithArray(String packageName, String className, String projectName,
			String[] fieldName, String[] fieldType) {
		if (!"undefined".equals(className)) {
			try {
				System.out.println("class name = " + className);
				String newPackage = packageName.replace(".", "/");
				FileWriter myWriter = new FileWriter("/Users/qc/Desktop/generator-test/" + projectName
						+ "/src/main/java/" + newPackage + "/model/" + className + ".java");
				myWriter.write("package " + packageName + ".model" + ";\n");
				myWriter.write("import javax.persistence.*;\n");
				myWriter.write("import java.io.Serializable;\n \n");
				myWriter.write("@Entity\n");
				myWriter.write("public class " + className + " implements Serializable {\n");
				myWriter.write("	private static final long serialVersionUID = 1L;\n");
				myWriter.write("\n");

				for (int j = 0; j < fieldName.length; j++) {
					myWriter.write("	private " + fieldType[j] + " " + fieldName[j] + ";\n");
				}
				myWriter.write("\n");
				for (int j = 0; j < fieldName.length; j++) {
					String fakeFieldName = fieldName[j].substring(0, 1).toUpperCase() + fieldName[j].substring(1);
					myWriter.write("	public " + fieldType[j] + " get" + fakeFieldName + "()\n");
					myWriter.write("	{\n");
					myWriter.write("		return " + fieldName[j] + ";\n");
					myWriter.write("	}\n");

					myWriter.write("	public void set" + fakeFieldName + "(" + fieldType[j] + " " + fieldName[j] + ")"
							+ "\n");
					myWriter.write("	{\n");
					myWriter.write("		this." + fieldName[j] + "=" + fieldName[j] + ";\n");
					myWriter.write("	}\n");

				}

				myWriter.write("\n");
				myWriter.write("}");
				myWriter.close();
				System.out.println("Successfully wrote to the file.");
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			System.out.println("class name null");
		}

	}

}
