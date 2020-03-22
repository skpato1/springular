package com.sifast.springular.framework.business.logic.Executor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;

@Component
public class JdlFileWriter {
	
	
	public void writeRelationshipsInJdlFile(Project project, FileWriter myWriter) {
		List<Integer> usedRelationshipsIds = new ArrayList<>();
		project.getEntities().stream().forEach(entity -> {
			entity.getRelationshipsMaster().stream().forEach(masterRelationship -> {
				addRelationshipToJdlFile(usedRelationshipsIds, masterRelationship, myWriter);
			});
			entity.getRelationshipsSlave().stream().forEach(slaveRelationship -> {
				addRelationshipToJdlFile(usedRelationshipsIds, slaveRelationship, myWriter);
			});
		});
	}

	public void writeEntitiesInJdlFile(Project project, FileWriter myWriter) {
		project.getEntities().stream().forEach(entity -> {
			try {
				writeEntityNameInJdlFile(myWriter, entity);
				entity.getAttributes().stream().forEach(attribute -> {
					try {
						writeAttributeNameInJdlFile(myWriter, attribute);
					} catch (IOException e) {
						e.printStackTrace();
					}

				});
				myWriter.write(Constants.ACCOLADE_FERMANTE + Constants.PATTERN_RETOUR_LIGNE);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
	}

	public void writeProjectDetailsInJdlFile(Project project, FileWriter myWriter) throws IOException {
		myWriter.write("application " + Constants.ACCOLADE_OUVRANT + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(
				Constants.PATTERN_TABULATION + "config " + Constants.ACCOLADE_OUVRANT + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(
				Constants.PATTERN_TABULATION + "baseName " + project.getNameProject() + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION + "packageName " + project.getPackageProject()
				+ Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION + "applicationType " + project.getTypeProject()
				+ Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION + "serverPort " + project.getPortProject()
				+ Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION + "enableHibernateCache false" + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write("entities *" + Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE + Constants.PATTERN_RETOUR_LIGNE);
	}

	public void writeAttributeNameInJdlFile(FileWriter myWriter, Attribute attribute) throws IOException {
		myWriter.write(Constants.PATTERN_TABULATION + attribute.getNameAttribute() + " " + attribute.getTypeAttribute()
				+ Constants.PATTERN_RETOUR_LIGNE);
	}

	public void writeEntityNameInJdlFile(FileWriter myWriter, BuisnessLogicEntity entity) throws IOException {
		myWriter.write(
				"entity " + entity.getNameEntity() + Constants.ACCOLADE_OUVRANT + Constants.PATTERN_RETOUR_LIGNE);
	}

	public void addRelationshipToJdlFile(List<Integer> usedRelationshipsIds, Relationship relationship,
			FileWriter myWriter) {
		if (!usedRelationshipsIds.contains(relationship.getId())) {
			writeRelationshipInFile(myWriter, relationship);
			usedRelationshipsIds.add(relationship.getId());
		}
	}

	public void writeRelationshipInFile(FileWriter myWriter, Relationship relationship) {
		try {
			if (!relationship.getTypeRelationship().equals(RelationshipTypeEnum.ManyToMany)) {
				myWriter.write("relationship " + relationship.getTypeRelationship() + Constants.ACCOLADE_OUVRANT
						+ Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_TABULATION + relationship.getMasterEntity().getNameEntity()
						+ Constants.ACCOLADE_OUVRANT + relationship.getSlaveEntity().getNameEntity().toLowerCase()
						+ Constants.ACCOLADE_FERMANTE + " " + "to " + relationship.getSlaveEntity().getNameEntity()
						+ Constants.PATTERN_RETOUR_LIGNE + Constants.ACCOLADE_FERMANTE);
			} else {
				myWriter.write("relationship " + relationship.getTypeRelationship() + Constants.ACCOLADE_OUVRANT
						+ Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_TABULATION + relationship.getMasterEntity().getNameEntity()+
						Constants.ACCOLADE_OUVRANT+relationship.getSlaveEntity().getNameEntity().toLowerCase()
						+Constants.ACCOLADE_FERMANTE+"to "+relationship.getSlaveEntity().getNameEntity()+
						Constants.ACCOLADE_OUVRANT+relationship.getMasterEntity().getNameEntity().toLowerCase()+
						Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE+Constants.ACCOLADE_FERMANTE);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
