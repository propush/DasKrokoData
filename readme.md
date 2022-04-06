# Das Kroko Data
Word corpus generator for games like
[Crocodile board game](https://add-hobby.ru/crocodil.html).

## Building
To build from source:</br>
```./gradlew build```</br>
The distribution is then placed in ```build/distribution``` folder. 

## Data preparation
Russian adjective and noun lists with frequency of usage can be downloaded from http://www.artint.ru/projects/frqlist/5000lemma.num.zip </br>
Russian n-gram list can be found at https://ruscorpora.ru/new/ngrams/2grams-3.zip </br>
Feel free to prepare your own data (see ```testData``` folder for examples).

## Running
To be run from inside distribution folder as:</br>
```./bin/dasKrokoData <n-gram count> <adjective file> <noun file> <n-gram file>```

The program would output n-grams to stdout and some runtime stats to stderr.

## Limitations
* Bad suffix manipulation for word matching.
* Some more optimizations needed.
