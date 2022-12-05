import Vue from "vue";
import Vuetify from "vuetify/lib/framework";

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: "#FF9800",
        secondary: "#FFE0B2",
        info: "#E65100",
      },
    },
  },
});
