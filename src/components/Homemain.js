import React from 'react';
import { Input, Button, notification, } from 'antd';
import Fetch from "../fetch";


class Homemain extends React.Component
{
    static defaultProps = {
        handleCount: () => { }
    }

    constructor(props)
    {
        super(props);
        this.state = {
            oriurl: '',
            shorturl: '',
        };
        this.fetch = new Fetch();
        this.handleChange = this.handleChange.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.handleCount = this.handleCount.bind(this);
        this.handleInsert = this.handleInsert.bind(this);
        this.handleSendtoback = this.handleSendtoback.bind(this);
    }
    string10to62(number)
    {
        var chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split(''),
            radix = chars.length,
            qutient = +number,
            mod = 0,
            arr = [];
        do
        {
            mod = qutient % radix;
            qutient = (qutient - mod) / radix;
            arr.unshift(chars[mod]);
        } while (qutient);
        return arr.join('');
    }
    handleChange(e)//input变化
    {
        this.setState({ oriurl: e.target.value });
        console.log(this.state.oriurl);
    }
    handleClick()   //生成短连接
    {
        var oriurl = this.state.oriurl;
        console.log(oriurl);
        console.log(this.state.oriurl);

        if (oriurl.substr(0, 7) !== "http://" && oriurl.substr(0, 8) !== "https://")
        {
            this.setState({ oriurl: "" });
            this.alert('ERROR', 'URL格式错误，请以http://或https://开头');
            return;
        } else
        {
            this.props.handleCount();
            this.handleCount();
        }
    }
    handleCount = async () =>
    {
        //校验用户生成的数量
        let userId = sessionStorage.getItem('user');

        const data = await this.fetch.fetchUserDailyCount(userId);
        console.log(data);
        if (data <= 9)
        {
            // callback(data);
            this.handleInsert();
        } else if (data >= 10)
        {
            // callback(data);
            //每天申请数量达上限
            this.alert('ERROR!', '申请数量达到上限!');
        }
    }
    handleInsert = async () =>
    {
        //校验用户生成的数量
        let userId = sessionStorage.getItem('user');
        console.log("fetch");
        //返回最新的id
        const urlId = await this.fetch.fetchLatestUrlId() + 1;
        this.handleSendtoback(urlId, userId);
    }
    handleSendtoback = (urlid, userId) =>
    {
        var oriurl = this.state.oriurl;
        var shorturl = this.state.shorturl;
        console.log(oriurl);
        console.log(this.state.oriurl);
        //id转成62进制
        shorturl = this.string10to62(urlid);
        this.setState({ shorturl: shorturl });

        //把长短url传到后端
        let formdata2 = new FormData();
        formdata2.append('oriURL', oriurl);
        formdata2.append('shortURL', shorturl);
        formdata2.append('user_id', userId);  //问题同上
        let opts2 = {
            method: "POST",
            body: formdata2,
            headers: {
                "Authorization": sessionStorage.getItem('token')
            }
        };
        this.fetch.fetchInsertUrl(opts2);
    }
    alert = (mess, des) =>
    {
        notification.open({
            message: mess,
            description: des,
        });
        clearTimeout(2000);
    };
    render()
    {
        return (
            <div>
                <Input placeholder="请输入http://或https://" onChange={this.handleChange} value={this.state.oriurl} />
                <Button onClick={this.handleClick}>生成</Button>
                {/*<Button onClick={this.handleDelete}>重置</Button>*/}
                <br />
                <text>{"短链接：" + this.state.shorturl}</text>
            </div>
        )
    }
}

export default Homemain;