package com.ancientmc.mason.paramscanner;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) throws IOException {
        mainI(args);
    }

    public static int mainI(String[] args) throws IOException {
        OptionParser parser = new OptionParser();
        OptionSpec<File> sourcesOpt = parser.acceptsAll(asList("s", "srcDir")).withRequiredArg().ofType(File.class);
        OptionSpec<File> csvOpt = parser.acceptsAll(asList("c", "csv")).withRequiredArg().ofType(File.class);

        OptionSet optSet = parser.parse(args);

        if(optSet.has(sourcesOpt) && optSet.has(csvOpt)) {
            File sources = optSet.valueOf(sourcesOpt);
            File csv = optSet.valueOf(csvOpt);
            ParamScanner.run(sources, csv);
            return 0;
        }
        return -1;
    }
}
