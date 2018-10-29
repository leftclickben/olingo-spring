package net.readify.olingo.demo.service;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.*;
import org.apache.olingo.commons.api.ex.ODataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DemoEdmProvider implements CsdlEdmProvider {

    private static final String NAMESPACE = "OData.Demo";
    private static final String CONTAINER_NAME = "Container";
    private static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);
    private static final String ET_PRODUCT_NAME = "Product";
    private static final FullQualifiedName ET_PRODUCT_FQN = new FullQualifiedName(NAMESPACE, ET_PRODUCT_NAME);
    static final String ES_PRODUCTS_NAME = "Products";

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) {
        if (!entityTypeName.equals(ET_PRODUCT_FQN)) {
            return null;
        }

        // create EntityType properties
        CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
        CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
        CsdlProperty  description = new CsdlProperty().setName("Description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

        // create CsdlPropertyRef for Key element
        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
        propertyRef.setName("ID");

        // configure EntityType
        CsdlEntityType entityType = new CsdlEntityType();
        entityType.setName(ET_PRODUCT_NAME);
        entityType.setProperties(Arrays.asList(id, name , description));
        entityType.setKey(Collections.singletonList(propertyRef));

        return entityType;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {
        if (!entityContainer.equals(CONTAINER) || !entitySetName.equals(ES_PRODUCTS_NAME)) {
            return null;
        }

        CsdlEntitySet entitySet = new CsdlEntitySet();
        entitySet.setName(ES_PRODUCTS_NAME);
        entitySet.setType(ET_PRODUCT_FQN);
        return entitySet;
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) {
        // This method is invoked when displaying the Service Document at e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName != null && !entityContainerName.equals(CONTAINER)) {
            return null;
        }

        CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
        entityContainerInfo.setContainerName(CONTAINER);
        return entityContainerInfo;
    }

    @Override
    public List<CsdlSchema> getSchemas() {
        // create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ET_PRODUCT_FQN));
        schema.setEntityTypes(entityTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        List<CsdlSchema> schemas = new ArrayList<>();
        schemas.add(schema);

        return schemas;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() {
        // create EntitySets
        List<CsdlEntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(CONTAINER, ES_PRODUCTS_NAME));

        // create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }

    @Override
    public CsdlAnnotations getAnnotationsGroup(FullQualifiedName targetName, String qualifier) throws ODataException {
        return null;
    }

    @Override
    public CsdlEnumType getEnumType(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlTypeDefinition getTypeDefinition(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlComplexType getComplexType(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public List<CsdlAction> getActions(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public List<CsdlFunction> getFunctions(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlTerm getTerm(FullQualifiedName fullQualifiedName) {
        return null;
    }

    @Override
    public CsdlSingleton getSingleton(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public CsdlActionImport getActionImport(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public CsdlFunctionImport getFunctionImport(FullQualifiedName fullQualifiedName, String s) {
        return null;
    }

    @Override
    public List<CsdlAliasInfo> getAliasInfos() {
        return null;
    }
}
