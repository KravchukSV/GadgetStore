function getIndex(list, id){
    for(var i = 0; i < list.length; i++){
        if(list[i].id === id){
            return i;
        }
    }

    return -1;
}

var smartphoneApi = Vue.resource('/smartphone{/id}')

Vue.component('smartphone-form',{
    props: ['smartphones', 'smartphoneAttr'],
    data: function(){
        return {
            id: '',
            brand: '',
            model: '',
            memory: null,
            storage: null,
            price: null,
            picture: '',
            color: ''
        }
    },
    watch: {
      smartphoneAttr: function(newVal, oldVal){
          this.id = newVal.id;
          this.brand = newVal.brand;
          this.model = newVal.model;
          this.memory = newVal.memory;
          this.storage = newVal.storage;
          this.price = newVal.price;
          this.picture = newVal.picture;
          this.color = newVal.color;
      }
    },
    template:
        '<div> ' +
            '<input type="text" placeholder="Write brand" v-model="brand" />' +
            '<input type="text" placeholder="Write model" v-model="model" />' +
            '<input type="text" placeholder="Write memory" v-model="memory" />' +
            '<input type="text" placeholder="Write storage" v-model="storage" />' +
            '<input type="text" placeholder="Write price" v-model="price" />' +
            '<input type="text" placeholder="Write color" v-model="color" />' +
            '<input type="text" placeholder="Write picture" v-model="picture" />' +
            '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function (){
            var smartphone = {id: this.id, brand: this.brand, model: this.model, memory: this.memory,
                storage: this.storage, price: this.price, picture: this.picture, color: this.color};

            if(this.id){
                smartphoneApi.update({id: this.id}, smartphone).then(result =>
                    result.json().then(data =>{
                        var index = getIndex(this.smartphones, data.id)
                        this.smartphones.splice(index, 1, data);

                        this.id = '';
                    })
                )
            }
            else{
                smartphoneApi.save({}, smartphone).then(result =>
                    result.json().then(data => {
                        this.smartphones.push(data);
                    })
                )
            }
            this.brand = '';
            this.model = '';
            this.memory = '';
            this.storage = '';
            this.price = '';
            this.picture = '';
            this.color = '';
        }
    }
});

Vue.component('smartphone-row',{
    props: ['smartphone', 'editSmartphone', 'smartphones'],
    template:
        '<div>' +
            '<div class=" col" style="height: 24rem;" @click="edit">' +
                '<div class="card bg-dark text-center btn-secondary zoom" style="width: 14rem;">' +
                    '<img :src="smartphone.picture" class="card-img-top" alt="...">' +
                    '<div class="card-body">' +
                        '<h5 class="card-title">{{ smartphone.brand }} {{smartphone.model}} {{smartphone.memory}}/{{smartphone.storage}}GB {{smartphone.color}}<br> </br> </h5>' +
                        '<hr>' +
                        '<h6 class="text-danger fw-bold text-end">{{ smartphone.price }} грн</h6>' +
                        '<a href="#" class="btn btn-danger">Купити</a>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>',
    methods:{
        edit: function (){
            this.editSmartphone(this.smartphone);
        },
        del: function (){
            smartphoneApi.remove({id: this.smartphone.id}).then(result => {
                if(result.ok){
                    this.smartphones.splice(this.smartphones.indexOf(this.smartphone), 1)
                }
            })
        }
    }
});

Vue.component('smartphones-list', {
    props: ['smartphones'],
    data: function (){
      return{
          smartphone: null
      }
    },
    template:
        '<div>' +
            '<smartphone-form :smartphones="smartphones" :smartphoneAttr="smartphone"/>' +
            '<div class="container">' +
                '<div class="row row-cols-auto">' +
                    '<smartphone-row v-for="smartphone in smartphones" :key="smartphone.id" :smartphone="smartphone" ' +
                    ':editSmartphone="editSmartphone" :smartphones="smartphones"/>' +
                '</div>'+
            '</div>' +
        '</div>',
    methods:{
        editSmartphone: function(smartphone){
            this.smartphone = smartphone;
        }
    }
});

var listSmartphones = new Vue({
    el: '#listSmartphones',
    template:
        '<div>' +
            '<div v-if="!profile">Необхідно авторизуватися через <a href="/login">Google</a></div>' +
            '<div v-else>{{profile.name}}&nbsp;<a href="/logout">Вийти</a></div>' +
            '<smartphones-list :smartphones="smartphones"/>' +
        '</div>',
    data: {
        smartphones: frontendData.smartphones,
        profile: frontendData.profile
    }
});