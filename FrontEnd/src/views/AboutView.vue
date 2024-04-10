<template>
<SidebarMenu/>
</template>

<script>
import SidebarMenu from '@/components/SideBar.vue'

export default {
  components: { SidebarMenu },
  data () {
    return {
      menu: [
        {
          header: 'Main Navigation',
          hiddenOnCollapse: true
        },
        {
          href: '/',
          title: 'Dashboard',
          icon: 'fa fa-user'
        },
        {
          href: '/charts',
          title: 'Charts',
          icon: 'fa fa-chart-area',
          child: [
            {
              href: '/charts/sublink',
              title: 'Sub Link'
            }
          ]
        }
      ]
    }
  }
}
// import SideBar from '@/components/SideBar.vue'
// import SidebarMenu from "@/App.vue";
//
// export default {
//   components: {SidebarMenu, SideBar }
// }
</script>
