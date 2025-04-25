En docs poner los archivos a analizar
Ejecutar IndexBuilder
Elegir entre TheSearcher o TheSearcherQueryParser

Index Builder:
- Cambiar para que IndexWriterConfig el analyzer que vaya a necesitar
- Ejecutar IndexBuider para que cree el indice en el directorio index

The searcher:  ---> Busquedas mas simples y especificas con las queries
- De ser necesario, cambiar myTerm para el que necesite
- Cambiar queryStr para lo que quiero buscar
Formato Resultados:
    position=1           score=  0,7650382
    doc=1 score=0.7650382 shardIndex=0
    Stored fields: Document<stored<path:C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\b.txt>>
    C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\b.txt

    position=2           score=  0,4684883
    doc=4 score=0.46848834 shardIndex=0
    Stored fields: Document<stored<path:C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\e.txt>>
    C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\e.txt

    position=3           score=  0,4190287
    doc=0 score=0.4190287 shardIndex=0
    Stored fields: Document<stored<path:C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\a.txt>>
    C:\Users\admin\Documents\ITBA\A2_2C\EDA\TP2c\Lucene\docs\a.txt


TheSearcherQueryParser: ---> Consultas mas complejas desde una cadena de texto
- QueryParser cambiar el analyzer
- cambiar queryStr para lo que quiero buscar
Formato Resultados:
    En la posición 1: score = 1,147557, documento = docid 1, archivo b.txt

    En la posición 2: score = 0,702733, documento = docid 4, archivo e.txt

    En la posición 3: score = 0,628543, documento = docid 0, archivo a.txt


