/**
 * Created by 汪锦玺 on 2019/7/1.
 */
var app = new Vue({
    el: '#app',
    data: {
        message: 'hello Vue'
    }
    // ,
    // beforeCreate: function () {
    //     console.group('beforeCreate 创建前状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el); //undefined
    //     console.log("%c%s", "color:red", "data   : " + this.$data); //undefined
    //     console.log("%c%s", "color:red", "message: " + this.message)
    // },
    // created: function () {
    //     console.group('created 创建完毕状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el); //undefined
    //     console.log("%c%s", "color:red", "data   : " + this.$data); //已被初始化
    //     console.log("%c%s", "color:red", "message: " + this.message); //已被初始化
    // },
    // beforeMount: function () {
    //     console.group('beforeMount 挂载前状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + (this.$el)); //已被初始化
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data); //已被初始化
    //     console.log("%c%s", "color:red", "message: " + this.message); //已被初始化
    // },
    // mounted: function () {
    //     console.group('mounted 挂载结束状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el); //已被初始化
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data); //已被初始化
    //     console.log("%c%s", "color:red", "message: " + this.message); //已被初始化
    // },
    // beforeUpdate: function () {
    //     console.group('beforeUpdate 更新前状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el);
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data);
    //     console.log("%c%s", "color:red", "message: " + this.message);
    // },
    // updated: function () {
    //     console.group('updated 更新完成状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el);
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data);
    //     console.log("%c%s", "color:red", "message: " + this.message);
    // },
    // beforeDestroy: function () {
    //     console.group('beforeDestroy 销毁前状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el);
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data);
    //     console.log("%c%s", "color:red", "message: " + this.message);
    // },
    // destroyed: function () {
    //     console.group('destroyed 销毁完成状态===============》');
    //     console.log("%c%s", "color:red", "el     : " + this.$el);
    //     console.log(this.$el);
    //     console.log("%c%s", "color:red", "data   : " + this.$data);
    //     console.log("%c%s", "color:red", "message: " + this.message)
    // }
});

var app2 = new Vue({
    el: "#app-2",
    data: {
        message: '页面加载于' + new Date().toLocaleString()
    }
});

var app3 = new Vue({
    el: '#app-3',
    data: {
        seen: true
    }
});

var app4 = new Vue({
    el: '#app-4',
    data: {
        todos: [{
            text: '学习Vue'
        }, {
            text: '学习java'
        }, {
            text: '整个牛项目'
        }]
    }
});

var app5 = new Vue({
    el: '#app-5',
    data: {
        message: 'hello vue.js!'
    },
    methods: {
        reverseMessage: function () {
            this.message = this.message.split('').reverse().join('')
        }
    }
});

var app6 = new Vue({
    el: '#app-6',
    data: {
        message: 'Helle Vue!'
    }
});

Vue.component('todo-item', {
    props: ['todo'],
    template: '<li>{{ todo.text }}</li>'
});

var app7 = new Vue({
    el: '#app-7',
    data: {
        groceryList: [
            {id: 0, text: 'Vegetables'},
            {id: 1, text: 'Cheese'},
            {id: 2, text: 'Whatever else humans are supposed to eat'}
        ]
    }
    // ,
    // created: function () {
    //     // console.log(this.groceryList[0].text);
    //     alert(this.groceryList[1].text);
    // },
    // mounted: function () {
    //     this.groceryList[1].text = 'hkjh';
    // }
});

var app8 = new Vue({
    el: '#app-8',
    data: {
        message: 'Hello'
    },
    computed: {
        reversedMessage: function () {
            // console.log("computed");
            return this.message.split('').reverse().join('');
        },
        now: function () {
            // console.log("computed");
            return Date.now();
        }
    },
    methods: {
        reversedMessage2: function () {
            // console.log("methods");
            return this.message.split('').reverse().join('');
        }
    }
});

// console.log(app8.reversedMessage);
// app8.message = "GoodBye";
// console.log(app8.reversedMessage);

// console.log(app8.now);
// console.log(app8.now);


// console.log(app8.reversedMessage2());
// app8.message = "Good";
// console.log(app8.reversedMessage2());

var app9 = new Vue({
    el: '#app-9',
    data: {
        firstName: 'Foo',
        lastName: 'Bar'
    },
    computed: {
        fullName: function () {
            return this.firstName + " " + this.lastName;
        }
    }
});

var watchExampleVM = new Vue({
    el: '#watch-example',
    data: {
        question: '',
        answer: 'I cannot give you an answer until you ask a question!'
    },
    watch: {
        // 如果 `question` 发生改变，这个函数就会运行
        question: function (newQuestion, oldQuestion) {
            this.answer = 'Waiting for you to stop typing...'
            this.debouncedGetAnswer()
        }
    },
    created: function () {
        // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
        // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
        // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
        // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
        // 请参考：https://lodash.com/docs#debounce
        this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
    },
    methods: {
        getAnswer: function () {
            if (this.question.indexOf('?') === -1) {
                this.answer = 'Questions usually contain a question mark. ;-)'
                return
            }
            this.answer = 'Thinking...';
            var vm = this;
            axios.get('https://yesno.wtf/api')
                .then(function (response) {
                    vm.answer = _.capitalize(response.data.answer)
                })
                .catch(function (error) {
                    vm.answer = 'Error! Could not reach the API. ' + error
                })
        }
    }
});

var app10 = new Vue({
    el: '#app-10',
    /*data: {
     // isActive: true,
     // hasError: true
     classObject: {
     active: true,
     'text-danger': false
     }
     }*/
    data: {
        isActive: true,
        error: null
    },
    computed: {
        classObject: function () {
            return {
                active: this.isActive && !this.error,
                'text-danger': this.error && this.error.type === 'fatal'
            }
        }
    }
});

var example1 = new Vue({
    el: '#example-1',
    data: {
        parentMessage: 'parent',
        items: [
            {message: 'Foo'},
            {message: 'Bar'}
        ],
        counter: 0
    }
});
example1.items = example1.items.filter(function (item) {
    return item.message.match(/Foo/)
});

var vm = new Vue({
    el: "#app-11",
    data: {
        items: ['a', 'b', 'c'],
        userProfile: {
            name: 'Anika'
        },
        numbers: [1, 2, 3, 4, 5],
        sorts: [3, 2, 1, 4, 5]
    },
    computed: {
        evenNumbers: function () {
            return this.numbers.filter(function (number) {
                return number % 2 === 0;
            });
        },
        evenSorts: function () {
            return this.sorts.sort(sortNumbers)
        }
    }
});
function sortNumbers(a, b) {
    return a - b;
}

vm.items[1] = 'x';
vm.items.length = 2;
Vue.set(vm.userProfile, 'age', 27);
vm.$set(vm.userProfile, 'age', 28);

Vue.component('todo-item', {
    template: '<li>{{ title }}<button v-on:click="$emit(\'remove\')">Remove</button></li>',
    props: ['title']
});

new Vue({
    el: "#todo-list-example",
    data: {
        newTodoText: "",
        todos: [
            {
                id: 1,
                title: 'Do the dishes'
            },
            {
                id: 2,
                title: 'Take out the trash',
            },
            {
                id: 3,
                title: 'Mow the lawn'
            }
        ],
        nextTodoId: 4
    },
    methods: {
        addNewTodo: function () {
            this.todos.push({
                id: this.nextTodoId++,
                title: this.newTodoText
            });
            this.newTodoText = ''
        }
    }
});

var example2 = new Vue({
    el: '#example-2',
    data: {
        name: "vew.js"
    },
    methods: {
        greet: function (event) {
            alert("Hello " + this.name + "!");
            if (event) {
                alert(event.target.tagName)
            }
        }
    }
});

new Vue({
    el: '#example-3',
    methods: {
        say: function (message) {
            alert(message)
        },
        warn: function (message, event) {
            if (event) event.preventDefault();
            alert(message)
        }
    }
});

new Vue({
    el: '#app-12',
    data: {
        message: '',
        checked: false,
        checkedNames: []
    }
});

new Vue({
    el: "#example-4",
    data: {
        picked: ''
    }
});

new Vue({
    el: "#example-5",
    data: {
        selected: ''
    }
});

new Vue({
    el: '#example-6',
    data: {
        selected: []
    }
});

Vue.component("button-counter", {
    data: function () {
        return {count: 0}
    },
    template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
});

Vue.component('blog-post', {
    props: ['title'],
    template: '<h3>{{ title }}</h3>'
});

new Vue({
    el: '#components-demo',
    data: {
        posts: [
            {id: 1, title: 'My journey with Vue'},
            {id: 2, title: 'Blogging with Vue'},
            {id: 3, title: 'Why Vue is so fun'}
        ]
    }
});

Vue.component('blog-post', {
    props: ['post'],
    template: '<div class="blog-post"><h3>{{ post.title }}</h3><button v-on:click="$emit(\'enlarge-text\',0.1)">Enlarge text</button><div v-html="post.content"></div></div>'
});
Vue.component('alert-box', {
    template: `<div class="demo-alert-box"><strong>Error!</strong><slot></slot></div>`
});
new Vue({
    el: '#blog-posts-events-demo',
    data: {
        posts: [
            {id: 1, title: 'My journey with Vue', content: 'safasf'},
            {id: 2, title: 'Blogging with Vue', content: 'safasf'},
            {id: 3, title: 'Why Vue is so fun', content: 'safasf'}
        ],
        postFontSize: 1
    },
    methods: {
        onEnlargeText: function (enlargeAmount) {
            this.postFontSize += enlargeAmount
        }
    }
});


Vue.component('blog-post-row', {
    template: '<tr><td>dsfdsf</td> </tr>'
});
new Vue({
    el: '#example-7'
});


Vue.component('base-checkbox', {
    model: {
        prop: 'checked',
        event: 'change'
    },
    props: {
        checked: 'Boolean'
    },
    updated: function () {
        console.log(checked)
    },
    template: '<input type="checkbox" v-bind:checked="checked" v-on:change="$emit(\'change\',$event.target.checked)" >'
});

Vue.component('navigation-link', {
    template: `<a v-bind:href="url" class="nav-link"><slot></slot></a>`
});

Vue.component('base-layout', {
    template: '<div class="container"><header><slot name="header"></slot></header><main><slot></slot></main><footer><slot name="footer"></slot></footer></div>'
});

const vue = new Vue({
    el: '#example-8'
});


$("#load_tab_base").html(
    "<div class=\"tab-pane\" id=\"tab_base\">\n  <div class=\"row\">\n    <div class=\"col-sm-6 \">\n      <section class=\"panel panel-default\">\n        <div class=\"panel-body\" data-id=\"51633745\" >\n          \n  <div class=\"bordb clearfix padb10 marb10\">\n    <div class=\"auto\">\n      <div class=\"lineh26 color3\">基本信息<\/div>\n      \n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    编号：\n  <\/div>\n  <div class=\"value j-text_asset_6ced94  \" data-column=\"text_asset_6ced94\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    类 型：\n  <\/div>\n  <div class=\"value j-text_asset_e311e7  \" data-column=\"text_asset_e311e7\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    客户名称：\n  <\/div>\n  <div class=\"value j-name  \" data-column=\"name\" data-value-format=\"\">\n    北京博科星通科技有限公司\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    姓名：\n  <\/div>\n  <div class=\"value j-company_name  \" data-column=\"company_name\" data-value-format=\"\">\n    朱海强\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    职位：\n  <\/div>\n  <div class=\"value j-text_asset_82edad  \" data-column=\"text_asset_82edad\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n        \n    <\/div>\n  <\/div>\n\n\n\n\n  <div class=\"bordb clearfix padb10 marb10\">\n    <div class=\"auto\">\n      <div class=\"lineh26 color3\">联系信息<\/div>\n      \n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    国家：\n  <\/div>\n  <div class=\"value j-text_asset_b8d298  \" data-column=\"text_asset_b8d298\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_area_show\">\n  <div class=\"name\">\n    Email：\n  <\/div>\n  <div class=\"value j-note  \" data-column=\"note\" data-value-format=\"text\">\n    <p><\/p>\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_area_show\">\n  <div class=\"name\">\n    座机：\n  <\/div>\n  <div class=\"value j-text_area_asset_6f7e19  \" data-column=\"text_area_asset_6f7e19\" data-value-format=\"\">\n    <p><\/p>\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    QQ：\n  <\/div>\n  <div class=\"value j-address.wangwang  \" data-column=\"address.wangwang\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    微信号：\n  <\/div>\n  <div class=\"value j-address.wechat  \" data-column=\"address.wechat\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    Skype：\n  <\/div>\n  <div class=\"value j-text_asset_38bf4c  \" data-column=\"text_asset_38bf4c\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit url_field_show\">\n  <div class=\"name\">\n    网址：\n  <\/div>\n    <div class=\"value  \" data-column=\"address.url\" data-value-format=\"url\">\n      --\n    <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_area_show\">\n  <div class=\"name\">\n    其他联系方式：\n  <\/div>\n  <div class=\"value j-text_area_asset_03e3ae  \" data-column=\"text_area_asset_03e3ae\" data-value-format=\"\">\n    <p><\/p>\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_area_show\">\n  <div class=\"name\">\n    备注1：\n  <\/div>\n  <div class=\"value j-text_area_asset_516a56  \" data-column=\"text_area_asset_516a56\" data-value-format=\"\">\n    <p><\/p>\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit geo_address_field_show\">\n  <div class=\"name\">\n    地址：\n  <\/div>\n  <div class=\"value  \" data-column=\"address.detail_address\" data-value-format=\"geo\">\n\n    <font><\/font>\n      <a target=\"_blank\" class=\"text-primary\" href=\"javascript:;\">查看地图<\/a>\n  <\/div>\n<\/div>\n\n<div class=\"list quick_edit text_field_show\">\n  <div class=\"name\">\n    传真：\n  <\/div>\n  <div class=\"value j-address.fax  \" data-column=\"address.fax\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n    <\/div>\n  <\/div>\n\n\n\n\n  <div class=\"bordb clearfix padb10 marb10\">\n    <div class=\"auto\">\n      <div class=\"lineh26 color3\">其他信息<\/div>\n      \n<div class=\"list quick_edit field_map_field_show\">\n  <div class=\"name\">\n    跟进状态：\n  <\/div>\n  <div class=\"value j-status  \" data-column=\"status_mapped\" data-value-format=\"\">\n    初访\n  <\/div>\n<\/div>\n\n<div class=\"list  field_map_field_show\">\n  <div class=\"name\">\n    资料来源：\n  <\/div>\n  <div class=\"value j-channel  \" data-column=\"channel_mapped\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n    <\/div>\n  <\/div>\n\n\n\n\n  <div class=\" clearfix padb10 marb10\">\n    <div class=\"auto\">\n      <div class=\"lineh26 color3\">系统信息<\/div>\n      \n<div class=\"list  text_field_show\">\n  <div class=\"name\">\n    业务类型：\n  <\/div>\n  <div class=\"value j-custom_field_template  \" data-column=\"custom_field_template.name\" data-value-format=\"\">\n    --\n  <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      负责人：\n    <\/div>\n    <div class=\"value link_entity   \">\n      良泉\n    <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      创建人：\n    <\/div>\n    <div class=\"value link_entity   \">\n      良泉\n    <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      前负责人：\n    <\/div>\n    <div class=\"value link_entity   \">\n      --\n    <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      所属部门：\n    <\/div>\n    <div class=\"value link_entity   \">\n      恋企网络\n    <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      前所属部门：\n    <\/div>\n    <div class=\"value link_entity   \">\n      --\n    <\/div>\n<\/div>\n\n<div class=\"list select2_field_show\">\n    <div class=\"name\">\n      所属公海：\n    <\/div>\n    <div class=\"value link_entity   \">\n      --\n    <\/div>\n<\/div>\n\n<div class=\"list  datetime_field_show\">\n  <div class=\"name\">\n    创建时间：\n  <\/div>\n  <div class=\"value j-created_at  \" data-column=\"created_at\" data-value-format=\"time\">\n    <time datetime=\"2019-07-30T19:51:29+08:00\">2019-07-30 19:51<\/time>\n  <\/div>\n<\/div>\n\n<div class=\"list  datetime_field_show\">\n  <div class=\"name\">\n    更新于：\n  <\/div>\n  <div class=\"value j-updated_at  \" data-column=\"updated_at\" data-value-format=\"time\">\n    <time datetime=\"2019-08-01T17:18:42+08:00\">2019-08-01 17:18<\/time>\n  <\/div>\n<\/div>\n\n    <\/div>\n  <\/div>\n\n\n\n\n        <\/div>\n      <\/section>\n    <\/div>\n\n    <div class=\"col-sm-6 \">\n    <\/div>\n  <\/div>\n  <div class=\"clearfix\"><\/div>\n<\/div>");