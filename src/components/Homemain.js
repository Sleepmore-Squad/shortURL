import React from 'react';
import {  Input, Button, } from 'antd';

const {TextArea} = Input;


class Homemain extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            url:'',
            shorturl:'',
        };
        this.handleChange=this.handleChange.bind(this);
        this.handleCreate=this.handleCreate.bind(this);
        this.handleDelete=this.handleDelete.bind(this);
    }

    handleChange(e)   //input变化
    {
        this.setState({url:e.target.value});
    }

    handleCreate(e)   //生成短连接
    {
        var oriurl=this.state.url;
        var shorturl='';

        //请求后端返回最新的id，或者在后端查找长连接？
        // eslint-disable-next-line
        var urlid=0;

        //校验
        //长连接是否在后端存在
        let formdata = new FormData();
        formdata.append('oriurl',oriurl);
        let opts = {
            method: "POST",
        };
        fetch('http://localhost:8181/user/',opts)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if(data === 0)
                {
                    let opts1 = {
                        method: "GET",
                    };
                    //返回最新的id
                    fetch('http://localhost:8181/user/',opts1)
                        .then((response) => {
                            return response.json();
                        })
                        .then((data) => {
                            urlid=data;
                        })

                    //id转成62进制


                    //把长短url传到后端
                    let formdata2 = new FormData();
                    formdata2.append('oriurl',oriurl);
                    formdata2.append('shorturl',shorturl);
                    let opts2 = {
                        method: "POST",
                        body:formdata2,
                    };
                    fetch('http://localhost:8181/url/insert',opts2)
                        .then((response) => {
                            return response.json();
                        })

                }
                else if(data === 1)
                {
                    //本身存在长连接，找短链接
                    let formdata3 = new FormData();
                    formdata3.append('oriurl',oriurl);
                    let opts3 = {
                        method: "POST",
                        body:formdata3,
                    };
                    fetch('http://localhost:8181/url/insert',opts3)
                        .then((response) => {
                            return response.json();
                        })
                        .then((data) => {
                            shorturl=data;
                        })

                }
            })

         this.setState({url:shorturl});
    }

    handleDelete(e)
    {
        this.setState({url:''});
    }


    render(){
     return(
         <div >
             <TextArea class="text" onchange={this.handleChange} value={this.state.url} rows={4} cols={5}/>
             <Button onClick={this.handleCreate}>生成</Button>
             <Button onClick={this.handleDelete}>重置</Button>
         </div>
     )
    }
}

export default Homemain ;