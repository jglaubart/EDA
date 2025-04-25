# Solución al Problema de Búsqueda con Lucene

## Cambios Realizados

1. **Actualización de los archivos de documentos**:
   - `a.txt`: "Fly Me to the Moon"
   - `b.txt`: "Fly Fly Away"
   - `c.txt`: "Bohemian Rhapsody"
   - `d.txt`: "To the Moon and Back"
   - `e.txt`: "Fly Like an Eagle"

2. **Cambio de SimpleAnalyzer a StandardAnalyzer**:
   - En `IndexBuilder.java`: Se cambió la línea 75 para usar StandardAnalyzer en lugar de SimpleAnalyzer.
   - En `TheSearcherQueryParser.java`: Se cambió la línea 51 para usar StandardAnalyzer en lugar de SimpleAnalyzer.

3. **Modificación del formato de salida**:
   - En `TheSearcherQueryParser.java`: Se modificó el formato de salida para mostrar la posición, score, docid y nombre del archivo para cada resultado.

## Resultados Esperados

Al ejecutar la consulta "content:Fly OR content:Moon" con StandardAnalyzer, se espera obtener los siguientes resultados:

1. Los documentos que contienen "Fly" o "Moon" deberían aparecer en los resultados.
2. Los documentos relevantes son:
   - `a.txt` (docid 0): "Fly Me to the Moon" (contiene tanto "Fly" como "Moon")
   - `b.txt` (docid 1): "Fly Fly Away" (contiene "Fly")
   - `d.txt` (docid 3): "To the Moon and Back" (contiene "Moon")
   - `e.txt` (docid 4): "Fly Like an Eagle" (contiene "Fly")

3. El orden de los resultados dependerá de los scores calculados por Lucene, pero se espera que los documentos que contienen ambos términos (como `a.txt`) tengan un score más alto.

## Pasos para Ejecutar

1. Compilar el proyecto:
   ```
   mvn compile
   ```

2. Ejecutar IndexBuilder para reconstruir el índice con StandardAnalyzer:
   ```
   mvn exec:java -Dexec.mainClass="core.IndexBuilder"
   ```

3. Ejecutar TheSearcherQueryParser para realizar la búsqueda:
   ```
   mvn exec:java -Dexec.mainClass="core.TheSearcherQueryParser"
   ```

## Explicación de la Solución

El cambio de SimpleAnalyzer a StandardAnalyzer afecta cómo se tokeniza y analiza el texto tanto durante la indexación como durante la búsqueda. StandardAnalyzer es más sofisticado que SimpleAnalyzer:

- **SimpleAnalyzer**: Divide el texto en tokens basados en caracteres no alfanuméricos y convierte todo a minúsculas.
- **StandardAnalyzer**: Utiliza una lógica más compleja para la tokenización, maneja mejor las palabras compuestas, elimina palabras comunes (stop words) y aplica otras optimizaciones.

Al usar StandardAnalyzer, la búsqueda "content:Fly OR content:Moon" debería producir resultados más relevantes y precisos.