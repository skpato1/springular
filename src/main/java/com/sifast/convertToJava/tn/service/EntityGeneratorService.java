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
	public String equalsParameter(String classname)
	{
		String eq=
				"    @Override\n" + 
				"    public boolean equals(Object o) {\n" + 
				"        if (this == o) {\n" + 
				"            return true;\n" + 
				"        }\n" + 
				"        if (!(o instanceof "+classname+")) {\n" + 
				"            return false;\n" + 
				"        }\n" + 
				"        return id != null && id.equals((("+classname+") o).id);\n" + 
				"    }";
		return eq;
	}
	public String hash()
	{
		String hsh=
				"	 @Override\n" + 
				"    public int hashCode() {\n" + 
				"        return 31;\n" + 
				"    }";
		return hsh;
	}
	public String toStr(String classname,String[] fieldName)
	{
		String str="";
		
		String str1=
				"	  @Override \n" + 
				"	  public String toString() {\n" + 
				"	  StringBuilder builder = new StringBuilder();\n" + 
				"	  builder.append(\""+classname+" [id=\");\n" + 
				"	  builder.append(id);\n";
		String str2=
				"        builder.append(\"]\");\n" + 
				"		return builder.toString();";
		for (int j = 0; j < fieldName.length; j++) {
		
				str=str+
				
				"        builder.append(\", "+fieldName[j]+"=\");\n" + 
				"        builder.append("+fieldName[j]+");\n" + 
				  
				"";
		}
		return str1+str+str2;
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
				myWriter.write(equalsParameter(className));
				myWriter.write("\n");
				myWriter.write(hash());
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
	public void generateEntityDynamicWithArrayAngular(String className, String[] fieldName, String[] fieldType) {
		if (!"undefined".equals(className)) {
			try {
				String modelPath="/Users/qc/Desktop/springular-framework/springular-framework-persistence/src/main/java/com/sifast/model";
				System.out.println("class name = " + className);
				String packageName="com.sifast";
				String newPackage = packageName.replace(".", "/");
				FileWriter myWriter = new FileWriter(modelPath+"/"+ className + ".java");
				myWriter.write("package " + packageName + ".model" + ";\n");
				myWriter.write("import javax.persistence.*;\n");
				myWriter.write("import java.io.Serializable;\n \n");
				myWriter.write("@Entity\n");
				myWriter.write("public class " + className + " implements Serializable {\n");
				myWriter.write("	private static final long serialVersionUID = 1L;\n");
				myWriter.write("\n");
				myWriter.write(
						"	@Id\n" + 
						"    @GeneratedValue(strategy = GenerationType.IDENTITY)\n" + 
						"    private int id;\n");

				for (int j = 0; j < fieldName.length; j++) {
					myWriter.write("	private " + fieldType[j] + " " + fieldName[j] + ";\n");
				}
				myWriter.write("\n");
				myWriter.write(
						"	public int getId() {\n" + 
						"        return id;\n" + 
						"    }\n" + 
						"\n" + 
						"    public void setId(int id) {\n" + 
						"        this.id = id;\n" + 
						"    }");
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
				myWriter.write(equalsParameter(className));
				myWriter.write("\n");
				myWriter.write(hash());
				myWriter.write("\n");
				myWriter.write(toStr(className, fieldName));
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
