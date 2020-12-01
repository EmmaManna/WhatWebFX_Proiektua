package ehu.isad.model;

import javafx.scene.control.Hyperlink;

import java.util.Arrays;

public class CmsMongo {
    private String target;
    private Plugins plugins;

    public class Plugins{
        private MetaGenerator MetaGenerator;
        private HerrialdeMongo Country;

        public MetaGenerator getMetaGenerator() {
            return MetaGenerator;
        }

        public void setMetaGenerator(MetaGenerator metaGenerator) {
            this.MetaGenerator = metaGenerator;
        }

        public HerrialdeMongo getCountry() {
            return Country;
        }

        public void setCountry(HerrialdeMongo country) {
            Country = country;
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


        public class HerrialdeMongo{
            private String[] module;
            private String[] string;

            public String getModule() {
                return module[0];
            }

            public void setModule(String[] module) {
                this.module = module;
            }

            public String getString() {
                return string[0];
            }

            public void setString(String[] string) {
                this.string = string;
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

    public Plugins getPlug(){
        return plugins;
    }

    public void setPlugins(Plugins plugins) {
        this.plugins = plugins;
    }

}
