import React, { Component } from 'react';
import { Input, Button, notification } from 'antd';
import 'antd/dist/antd.css';
import '../css/login.css';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import Fetch from "../fetch";


class Login extends Component
{
    static defaultProps = {
        login1: () => { },
        login2: () => { },
        login3: () => { },
        usernameisnull:() => { },
        passwordisnull: () => { },
        register1: () => { },

    };

    constructor(props)
    {
        super(props);
        this.state = {
            username: '',
            password: '',
        }
        this.fetch = new Fetch();
        this.userChange = this.userChange.bind(this);
        this.passwordChange = this.passwordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSignUp = this.handleSignUp.bind(this);
        this.checkuser = this.checkuser.bind(this);
    }

    userChange(e)
    {
        this.setState({ username: e.target.value })
    };

    passwordChange(e)
    {
        this.setState({ password: e.target.value })
    };

    handleSubmit = async () =>
    {
        if (this.state.username === '')
        {
            this.props.usernameisnull();
            this.alert('ERROR!', 'Please input your username!');
            return;
        }
        else if (this.state.password === '')
        {
            this.props.passwordisnull();
            this.alert('ERROR!', 'Please input your password!');
            return;
        }//先判断用户名和密码是否为空
        else
        {
            let formdata = new FormData();
            for (const key in this.state)
            {
                formdata.append(key, this.state[key]);
            }

            let opts = {
                method: "POST",
                body: formdata,

            };

            const data = await this.fetch.fetchSubmitData(opts);
            console.log(data);
            this.checkuser(data);
        }

    };

    checkuser = (data) =>
    {
        console.log(data.code);
        if (data.code === 201)
        {
            sessionStorage.setItem('user', data.id);
            sessionStorage.setItem('isAdmin', '0');
            sessionStorage.setItem('token', data.data);
            this.props.history.replace({ pathname: '/home' });
            this.props.login1();
        }
        else if (data.code === 401)
        {
            sessionStorage.setItem('user', data.id);
            sessionStorage.setItem('isAdmin', '1');
            this.props.history.replace({ pathname: '/adminhome' });
            this.props.login2();
        }
        else
        {
            //if (data.code === 101)
            this.alert('WARNING!', 'Invalid username or password');
            this.props.login3();
        }
    };


    handleSignUp = () =>
    {

        this.props.register1();
        this.props.history.replace({ pathname: '/register' });
    }

    alert = (mess, des) =>
    {
        notification.open({
            message: mess,
            description: des,
        });
    };

    render()
    {
        return (
            <div className="login-page">
                <div className="login-container">
                    <div className="login-box">
                        <h1 className="page-title">LOGIN</h1>
                        <div className="login-content">
                            <Input className="input" size="large" placeholder="username" prefix={<UserOutlined />} onChange={this.userChange} value={this.state.username} />
                            <br />
                            <Input className="inputpassword" type="password" size="large" placeholder="password" prefix={<LockOutlined />} onChange={this.passwordChange} value={this.state.password} />
                            <br />
                            <br />

                            <Button className="register" shape="round" size="large" onClick={this.handleSignUp} >register</Button>
                            <Button className="login1" type="primary" shape="round" size="large" style={{ float: "right" }} onClick={this.handleSubmit}>login</Button>

                        </div>
                    </div>
                </div>

            </div>
        );


    }
}


export default Login;