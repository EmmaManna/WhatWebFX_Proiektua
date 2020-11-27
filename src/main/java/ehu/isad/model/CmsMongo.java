package ehu.isad.model;

import java.util.Arrays;

public class CmsMongo {
    private String target;

    private Plugins plugins;

    public class Plugins{
        private MetaGenerator MetaGenerator;

        public MetaGenerator getMetaGenerator() {
            return MetaGenerator;
        }

        public void setMetaGenerator(MetaGenerator metaGenerator) {
            this.MetaGenerator = metaGenerator;
        }


        public class MetaGenerator{
            private String[] string;

            public String[] getString() {
                return string;
            }

            public void setString(String[] string) {
                this.string = string;
            }

            @Override
            public String toString() {
                return Arrays.toString(string);
            }
        }
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPlugins() {
        try{
            var emaitza=plugins.getMetaGenerator().toString();
            return emaitza;
        }
        catch (NullPointerException e){
            return "unknown";
        }
    }

    public void setPlugins(Plugins plugins) {
        this.plugins = plugins;
    }

}
