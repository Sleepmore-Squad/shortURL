import React, {Component} from 'react';
import {Input, Button, notification} from 'antd';
import 'antd/dist/antd.css';
import '../css/login.css';
import {UserOutlined, LockOutlined} from '@ant-design/icons'


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
        };
        this.userChange = this.userChange.bind(this);
        this.passwordChange = this.passwordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSignUp = this.handleSignUp.bind(this);
        this.checkuser = this.checkuser.bind(this);
    }

    userChange(e) {
        this.setState({username: e.target.value});
        //console.log(this.state.username);
    };

    passwordChange(e) {
        this.setState({password: e.target.value})
    };

    handleSubmit = () => {
        if (this.state.username === '') {
            this.alert('ERROR!', 'Please input your username!');
            return;
        } else if (this.state.password === '') {
            this.alert('ERROR!', 'Please input your password!');
            return;
        }//先判断用户名和密码是否为空

        let formdata = new FormData();
        for (const key in this.state) {
            formdata.append(key, this.state[key]);
        }
        let opts = {
            method: "POST",
            body: formdata,

        };

        fetch('http://localhost:8181/user/login', opts)
            .then((response) => {
                return response.json()
            })
            .then((data) => {
                console.log(data);
                this.checkuser(data);
            })

    };

    checkuser = (data) => {
        if (data != null) {
            if (data.code === 201) {
                sessionStorage.setItem('user', data.id);
                sessionStorage.setItem('isAdmin', '0');
                sessionStorage.setItem('token', data.data);
                this.props.history.replace({pathname: '/home'});
            } else if (data.code === 401) {
                sessionStorage.setItem('user', data.id);
                sessionStorage.setItem('isAdmin', '1');
                sessionStorage.setItem('token', data.data);
                this.props.history.replace({pathname: '/adminhome'});
            } else
                //if (data.code === 101)
                this.alert('WARNING!', 'Invalid username or password');
        }
    };


    handleSignUp = () => {
        this.props.history.replace({pathname: '/register'});
    };

    alert = (mess, des) => {
        notification.open({
            message: mess,
            description: des,
        });
    };

    render() {
        return (
            <div className="login-page">
                <div className="login-container">
                    <div className="login-box">
                        <h1 className="page-title">LOGIN</h1>
                        <div className="login-content">

                            <Input size="large" placeholder="username" prefix={<UserOutlined/>}
                                   onChange={this.userChange}/>
                            <br/>
                            <Input.Password size="large" placeholder="password" prefix={<LockOutlined/>}
                                            onChange={this.passwordChange}/>
                            <br/>
                            <br/>
                            <Button shape="round" size="large" onClick={this.handleSignUp}>register</Button>
                            <Button type="primary" shape="round" size="large" style={{float: "right"}}
                                    onClick={this.handleSubmit}>login</Button>

                        </div>
                    </div>
                </div>

            </div>
        );


    }
}


export default Login;