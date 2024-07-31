# XML-DTD_Parser
## ¿Qué es XML-DTD-Parser? 
Este proyecto fue realizado para la asignatura de **Programación Orientada a Objetos**, consiste en una librería capaz de intepretar archivos **DTD** y **XML** haciendo uso únicamente de funciones presentes de forma nativa en **Java**(i.e. no librerías ni dependencias externas).

## Librería XML
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
Una vez instanciada la clase `XMLParser`, es necesario llamar al método `parse` para realizar el **parseo** del archivo *XML*, nuevamente, el método puede recibir o no la ruta del archivo **XML**. Este método devuelve un objeto `XMLTree`.
### Ejemplos de uso 
#### Ejemplo #1:
Se puede instanciar primero el objeto `XMLParser`, para después enviar la ruta en el método `parse` (veáse que se retorna un objeto `XMLTree`).
```java
XMLParser xmlParser = new XMLParser(); // Instanciar el objeto XMLParser
XMLTree xmlTree = xmlParser.parse(XML_FILE); // Llamar al método parse con XML_FILE como parámetro
```
#### Ejemplo #2:
Se puede instanciar el objeto `XMLParser`, enviando como parámetro del constructor la ruta del archivo **XML**, sólo para después llamar al método `parse`.
```java
XMLParser parser = new XMLParser(XML_FILE);
XMLTree tree = parser.parse();
```
### Imprimir árbol
Si se quiere imprimir el árbol, hay una clase destinada a ello. Se debe llamar, de manera estática, al método `printTree` de la clase `TreePrinter`, donde el método recibe como parámetros la raíz del árbol (`node`), y el nivel del árbol (`level`).
```java
/**
 * Print the tree structure
 * 
 * @param node  the node to print
 * @param level the level of the node
 */
public static void printTree(TagNode node, int level) {
    for (int i = 0; i < level; i++)
        System.out.print("  ");
    System.out.println(node.getName());

    if (node.getContent() != null) {
        for (int i = 0; i < level; i++)
            System.out.print("  ");
        System.out.println("Content: " + node.getContent());
    }

    if (!node.getAttributes().isEmpty()) {
        for (Attribute attribute : node.getAttributes()) {
            for (int i = 0; i < level; i++)
                System.out.print("  ");
            System.out.println(attribute.getName() + " = " + attribute.getValue());
        }
    }
    for (TagNode child : node.getChildren())
        printTree(child, level + 1);
    }
```
#### Ejemplo de uso:
```java
TreePrinter.printTree(tree.getRoot(), 0);
```

### Manejo de excepciones
La librería de encarga de manejar las excepciones posibles, tanto errores `sintácticos` cómo `lógicos`, esto lo hace mediante la clase `ErrorHandler`, que cuenta con métodos destinados a enviar errores, diciendo **detalles** como el mensaje de error y la línea en la que se lanzó el mismo:
```java
/**
 * Function to throw an error
 * @param message Message to show
 * @param line Line where the error is located
  */
public static void throwError(String message, int line) {
    throw new RuntimeException("[Error at line " + line + "] " + message);
}

/**
 * Function to throw an error
 * @param message Message to show
  */
public static void throwError(String message) {
    throw new RuntimeException("[Error] " + message);
}
```
## Librería DTD
Hasta este punto se ha manejado el archivo **XML**, sin embargo, si dicho archivo cuenta con un enlace a un archivo **DTD**, entonces es posible utilizar la clase `DTDParser` para tratar con dicho archivo.

El funcionamiento es relativamente simple, en primer momento, es necesario instanciar el objeto `DTDParser`, donde los constructores son los siguientes: 
1. Un **constructor** que no recibe parámetros: 
```java
/**
 * Constructor
  */
public DTDParser(){}
```
2. Un **constructor** que recibe como parámetro la ruta al archivo **DTD**:
```java
/**
 * Constructor with path
 * @param path the path of the DTD file
  */
public DTDParser(String path){
    this.dtdPath = path;
}
```
Una vez instanciada la clase, es necesario llamar al método `parse()` de la clase, esté método nuevamente puede o no recibir la ruta al archivo **DTD**. Este método debe recibir la ruta al archivo **DTD**, donde hay dos maneras de conseguir dicha ruta: 
1. Directamente colocar la ruta del archivo DTD dentro del código.
2. Utilizar el método `getDtdPath()` del objeto `XMLTree` para obtener la ruta extraída por el **parser** XML en tiempo de ejecución.

### Ejemplo de uso
#### Ejemplo #1:
Se puede instanciar primero el objeto y después enviarle la ruta del archivo **DTD** en el método `parse()`:
```java
DTDParser dtdParser = new DTDParser(); 
DTDRestrictions dtdRestrictions = dtdParser.parse(xmlTree.getDtdPath()); 
```
#### Ejemplo #2: 
Se puede enviar la ruta del archivo **DTD** al constructor, y después llamar al método `parse()`.
```java
DTDParser dtdParser = new DTDParser(xmlTree.getDtdPath()); 
DTDRestrictions dtdRestrictions = dtdParser.parse(); 
```

## Interpretación del archivo
Una vez *instanciado* y *parseado* el archivo, es necesario pasar a un último paso, que es **interpretar** las reglas obtenidas. Para ello se utiliza la clase `DTDInterpreter`, que puede instanciarse de dos formas: 
1. Un **constructor** que recibe un árbol de tipo `XMLTree` obtenido por el **parser** XML, así como también un objeto `DTDRestrictions`, obtenido por el **parser** DTD:
```java
/**
 * Constructor with XML tree and DTD restrictions as parameters
 * @param xmlTree the XML tree
 * @param dtdRestrictions the restrictions of the DTD file
  */
public DTDInterpreter(XMLTree xmlTree, DTDRestrictions dtdRestrictions) {
    this.xmlTree = xmlTree;
    this.dtdRestrictions = dtdRestrictions;
}
```
2. Un **constructor** vacío, que sirve solamente para instanciar el objeto: 
```java
/**
 * Constructor
  */
public DTDInterpreter() {}
```
Por último, para interpretar las reglas, es necesario llamar al método `interpret()`, de la clase `DTDInterpreter`, que también puede recibir el árbol `XMLTree` y `DTDRestrictions` si es que no fueron enviados al constructor antes.
### Ejemplos de uso:
#### Ejemplo #1:
En este ejemplo, se envía el objeto `XMLTree` y el objeto `DTDRestrictions` al **constructor** y posteriormente se llama al método `interpret()`.
```java
DTDInterpreter dtdInterpreter = new DTDInterpreter(xmlTree, dtdRestrictions); 
dtdInterpreter.interpret(); 
```
#### Ejemplo #2:
Primero, se instancia el objeto y posteriormente, en el método `interpret()`, se envían los parámetros necesarios: 
```java
DTDInterpreter dtdInterpreter = new DTDInterpreter(); 
dtdInterpreter.interpret(xmlTree, dtdRestrictions); 
```

## Ejemplo completo de uso
```java
XMLParser xmlParser = new XMLParser(); // Objeto del parser XML
XMLTree xmlTree = xmlParser.parse(XML_FILE); // Método parse() que retorna un objeto XMLTree
TreePrinter.printTree(xmlTree.getRoot(), 0);

DTDParser dtdParser = new DTDParser(); // Objeto parser DTD
DTDRestrictions dtdRestrictions = dtdParser.parse(xmlTree.getDtdPath()); // Método parse() que retorna un objeto DTDRestrictions

DTDInterpreter dtdInterpreter = new DTDInterpreter(); // Objeto interpréte del DTD
dtdInterpreter.interpret(xmlTree, dtdRestrictions); // Método interpret() que interpreta el archivo DTD y XML a la vez
```
Todo esto está pensado para ir dentro de un bloque **TryCatch**, si no se produce ninguna excepción durante la ejecución de dicho bloque, quiere decir que tanto el archivo `XML` como el `DTD` son totalmente correctos.