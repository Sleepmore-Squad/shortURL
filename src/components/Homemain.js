import React from 'react';
import {Input, Button, notification,} from 'antd';




class Homemain extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            oriurl:'',
            shorturl:'',
        };
        this.handleChange=this.handleChange.bind(this);
        this.handleCreate=this.handleCreate.bind(this);
        // this.handleDelete=this.handleDelete.bind(this);
    }

    handleChange = (e)  => //input变化
    {
        this.setState({oriurl:e.target.value});
    }

    handleCreate(e)   //生成短连接
    {
        var oriurl=this.state.oriurl;
        var shorturl=this.state.shorturl;

        if(oriurl.substr(0, 7) != "http://" && oriurl.substr(0, 8) != "https://")
        {
            this.setState({oriurl:""});
            this.alert('ERROR','URL格式错误，请以http://或https://开头');
            return;
        }
        //请求后端返回最新的id
        var urlid=0;

        //校验用户生成的数量
        let username=sessionStorage.getItem('user');
        //时间
        let d=new Date();
        let year=d.getFullYear();
        let month=d.getMonth()+1;
        let day=d.getDate();
        let date=year+'-'+month+'-'+day;

        let formdata = new FormData();
        formdata.append('id',username);//没有端口给到我user_id这里需要修改
        formdata.append('date',date);//没有端口给到我user_id这里需要修改
        let opts = {
            method: "POST",
            body:formdata,
        };
        fetch('http://localhost:8181/order/count',opts)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if(data <= 9 )
                {
                    let opts1 = {
                        method: "GET",
                    };
                    //返回最新的id
                    fetch('http://localhost:8181/url/getCount',opts1)
                        .then((response) => {
                            return response.json();
                        })
                        .then((data) => {
                            urlid=data;
                        })

                    urlid+=1;

                    //id转成62进制
                    shorturl = urlid.toString(62);

                    //把长短url传到后端
                    let formdata2 = new FormData();
                    formdata2.append('oriURL',oriurl);
                    formdata2.append('shortURL',shorturl);
                    formdata2.append('user_id',username);  //问题同上
                    let opts2 = {
                        method: "POST",
                        body:formdata2,
                    };
                    fetch('http://localhost:8181/url/insert',opts2)
                        .then((response) => {
                            return response.json();
                        })

                }
                else if(data >= 10)
                {
                    //每天申请数量达上限
                    this.alert('ERROR!','申请数量达到上限!');
                    return;
                }
            })

         this.setState({shorturl:shorturl});
    }

    alert = (mess,des) =>{
        notification.open({
            message: mess,
            description: des,
        });
        clearTimeout(2000);
    }

    // handleDelete(e)
    // {
    //     this.setState({oriurl:''});
    // }


    render(){
     return(
         <div >
             <Input type="textarea" class="text" placeholder="请输入http://或https://" onchange={this.handleChange}  rows={4}/>
             <Button onClick={this.handleCreate}>生成</Button>
             {/*<Button onClick={this.handleDelete}>重置</Button>*/}
             <br/>
             <text>{"短链接："+this.state.shorturl}</text>
         </div>
     )
    }
}

export default Homemain ;