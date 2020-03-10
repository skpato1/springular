package com.sifast.convertToJava.tn.service;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sifast.convertToJava.tn.common.Constants;

@Service
public class EntityGeneratorService {
	
	
	public String equalsParameter(String classname)
	{
		String eq=
				"    @Override" +Constants.PATTERN_RETOUR_LIGNE+ 
				"    public boolean equals(Object o) " +Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"        if (this == o) " +Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"            return true"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE+ 
				"       "+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE+  
				"        if (!(o instanceof "+classname+")) "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE+ 
				"            return false"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE+ 
				"        "+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE + 
				"        return id != null && id.equals((("+classname+") o).id)"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"    "+Constants.ACCOLADE_FERMANTE;
		return eq;
	}
	public String hash()
	{
		String hsh=
				"	 @Override"+Constants.PATTERN_RETOUR_LIGNE + 
				"    public int hashCode() "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE + 
				"        return 31"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"    "+Constants.ACCOLADE_FERMANTE;
		return hsh;
	}
	public String toStr(String classname,String[] fieldName)
	{
		String str="";
		
		String str1=
				"	  @Override "+Constants.PATTERN_RETOUR_LIGNE + 
				"	  public String toString() "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE + 
				"	  StringBuilder builder = new StringBuilder()"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"	  builder.append(\""+classname+" [id=\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"	  builder.append(id)"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE;
		String str2=
				"        builder.append(\"]\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"		return builder.toString()"+Constants.PATTERN_POINT_VIRGULE;
		for (int j = 0; j < fieldName.length; j++) {
		
				str=str+
				
				"        builder.append(\", "+fieldName[j]+"=\")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				"        builder.append("+fieldName[j]+")"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE + 
				  
				"";
		}
		return str1+str+str2;
	}
		public void generateEntityDynamicWithArrayAngular(String className, String[] fieldName, String[] fieldType) {
		if (!"undefined".equals(className)) {
			try {
				String packageName="com.sifast";
				FileWriter myWriter = new FileWriter(Constants.PATTERN_PATH_TO_PACKAGE_MODEL+"/"+ className + ".java");
				myWriter.write("package " + packageName + ".model" +Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write("import javax.persistence.*"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write("import java.io.Serializable"+Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("@Entity"+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("public class " + className + " implements Serializable "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write("	private static final long serialVersionUID = 1L"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(
						"	@Id" + Constants.PATTERN_RETOUR_LIGNE+
						"    @GeneratedValue(strategy = GenerationType.IDENTITY)"+Constants.PATTERN_RETOUR_LIGNE + 
						"    private int id"+Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);

				for (int j = 0; j < fieldName.length; j++) {
					myWriter.write("	@Column(name = \""+fieldName[j]+"\")"+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write("	private " + fieldType[j] + " " + fieldName[j] + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
				}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(
						"	public int getId() " +Constants.ACCOLADE_OUVRANT+ Constants.PATTERN_RETOUR_LIGNE+
						"        return id"+ Constants.PATTERN_POINT_VIRGULE+ Constants.PATTERN_RETOUR_LIGNE+
						"    "+Constants.ACCOLADE_FERMANTE +Constants.PATTERN_RETOUR_LIGNE+ 
						Constants.PATTERN_RETOUR_LIGNE+ 
						"    public void setId(int id) "+Constants.ACCOLADE_OUVRANT+ Constants.PATTERN_RETOUR_LIGNE+ 
						"        this.id = id"+Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE+ 
						Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				for (int j = 0; j < fieldName.length; j++) {
					String fakeFieldName = fieldName[j].substring(0, 1).toUpperCase() + fieldName[j].substring(1);
					myWriter.write(Constants.PATTERN_TABULATION+"public " + fieldType[j] + " get" + fakeFieldName + "()"+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.PATTERN_TABULATION+"return " + fieldName[j] + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);

					myWriter.write(Constants.PATTERN_TABULATION+"public void set" + fakeFieldName + "(" + fieldType[j] + " " + fieldName[j] + ")"
							+ Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.PATTERN_TABULATION+"this." + fieldName[j] + "=" + fieldName[j] + Constants.PATTERN_POINT_VIRGULE+Constants.PATTERN_RETOUR_LIGNE);
					myWriter.write(Constants.PATTERN_TABULATION+Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
				}
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(equalsParameter(className));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(hash());
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(toStr(className, fieldName));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ACCOLADE_FERMANTE);
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
