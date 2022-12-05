<template>
  <v-app>
    <the-header-nav></the-header-nav>
    <v-main>
      <v-slide-y-transition mode="out-in">
        <router-view />
      </v-slide-y-transition>
    </v-main>
    <the-footer></the-footer>
  </v-app>
</template>

<script>
import TheHeaderNav from "@/components/TheHeaderNav.vue";
import TheFooter from "@/components/TheFooter.vue";
export default {
  name: "App",
  components: {
    TheHeaderNav,
    TheFooter,
  },
  data: () => ({
    //
  }),
};
</script>

<style>
/* yellow :  #F5dF4D , gray : #939597 blue: color="#73ceff"*/

#app {
  background: linear-gradient(0deg, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.8)), url("@/assets/background3.jpg");
  background-size: 100% 50%;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
</style>
