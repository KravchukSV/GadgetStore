var smartphoneFilterApi = Vue.resource('/smartphone/filter')

Vue.component('checkbox-list', {
    props: ['items', 'checkFilter', 'type'],
    template:
        '<div class="bg-dark px-2 ">' +
            '<h6 class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggleExternalContent" >{{ type }}</h6>' +
            '<div class="collapse" v-for="item in items" :key="item" id="navbarToggleExternalContent">' +
                '<div class="form-check">' +
                    '<input class="form-check-input" type="checkbox" :id="`${type}Check-${item}`" @click="toggleFilter(item)">' +
                    '<label class="form-check-label" :for="`${type}Check-${item}`">' +
                        '{{ item }}' +
                    '</label>' +
                '</div>' +
             '</div>' +
        '</div>',
    methods:{
        toggleFilter: function (item) {
            if(this.checkFilter[this.type].includes(item)){
                this.checkFilter[this.type] = this.checkFilter[this.type].filter(element => element !== item);
            }
            else{
                this.checkFilter[this.type].push(item);
            }
            this.applyFilter();
        },
        applyFilter: function() {
            axios.post('/smartphone/filter', this.checkFilter)
                .then(result => {
                    listSmartphones.smartphones = result.data;
                });
        }
    }
});

var filterSmartphones = new Vue({
    el: '#filterSmartphones',
    template:
        '<div>' +
            '<checkbox-list v-for="(items, type) in filter" :key="type" :items="items" :checkFilter="checkFilter" :type="type" />' +
        '</div>',

    data: {
        filter: {
            listBrand: [],
            listModel: [],
            listMemory: [],
            listStorage: [],
            listColor: [],
        },
        checkFilter: {
            listBrand: [],
            listModel: [],
            listMemory: [],
            listStorage: [],
            listColor: []
        }
    },

    created: function () {
        smartphoneFilterApi.get()
            .then(result => result.json())
            .then(data => this.filter = data);
    }
});

