# XML-DTD_Parser
## ¿Qué es XML-DTD-Parser? 
Este proyecto fue realizado para la asignatura de **Programación Orientada a Objetos**, consiste en una librería capaz de intepretar archivos **DTD** y **XML** haciendo uso únicamente de funciones presentes de forma nativa en **Java**(i.e. no librerías ni dependencias externas).

## ¿Cómo usarlo? 
El funcionamiento de la librería es relativamente simple: 

El primer paso es declarar una instancia del objeto `XMLParser`, los constructores del objeto son los siguientes: 

1. Un **constructor** que puede recibir un **String** `path` como parámetro, dicho argumento debe ser la ruta del archivo **XML**:
```java
public XMLParser(String path){  
    this.path = path;
}
```
2. Un **constructor** que no recibe nada, y solo sirve para instanciar el objeto: 
```java
public XMLParser(){}
```

Una vez instanciada la clase `XMLParser`, es necesario llamar al método `parse` para realizar el **parseo** del archivo *XML*, nuevamente, el método puede recibir o no la ruta del archivo **XML**.
### Ejemplos de uso 
#### Ejemplo #1:
```java
XMLParser xmlParser = new XMLParser(); // Instanciar el objeto XMLParser
XMLTree xmlTree = xmlParser.parse(XML_FILE); // Llamar al método parse con XML_FILE como parámetro
```