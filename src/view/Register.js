import React, {Component} from 'react';
import {Input, Button, notification} from 'antd';
import 'antd/dist/antd.css';
import '../css/register.css'
import {UserOutlined, LockOutlined, MailOutlined} from '@ant-design/icons'


class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password1: '',
            password2: '',
            email: '',
            code: '',
        }
        this.userChange = this.userChange.bind(this);
        this.password1Change = this.password1Change.bind(this);
        this.password2Change = this.password2Change.bind(this);
        this.emailChange = this.emailChange.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleRegister = this.handleRegister.bind(this);
        this.checkregister = this.checkregister.bind(this);
        this.handlemailtest = this.handlemailtest.bind(this);
        this.codeChange = this.codeChange.bind(this);
        this.alert = this.alert.bind(this);
    }

    userChange(e) {
        this.setState({username: e.target.value})
    };

    password1Change(e) {
        this.setState({password1: e.target.value})
    };

    password2Change(e) {
        this.setState({password2: e.target.value})
    };

    emailChange(e) {
        this.setState({email: e.target.value})
    };

    codeChange(e) {
        this.setState({code: e.target.value})
    };

    //发送验证码
    handlemailtest = () => {
        let formdata = new FormData();
        formdata.append('email', this.state.email);
        let opts = {
            method: "POST",
            body: formdata,
        };
        fetch('http://34.203.103.12:8181/user/register/send', opts)
            .then((response) => {
                this.alert('SENT!', 'Verification Code is sent!')
                return response.json();
            })
    };


    handleCancel = () => {
        this.props.history.replace({pathname: '/'});
    };

    handleRegister = () => {
        if (this.state.username === '') {
            this.alert('ERROR!', 'Please input  username!');
            return;
        } else if (this.state.password1 === '') {
            this.alert('ERROR!', 'Please input  password!');
            return;
        } else if (this.state.email === '') {
            this.alert('ERROR!', 'Please input  email!');
            return;
        } else if (this.state.code === '') {
            this.alert('ERROR!', 'Please input verification code!');
            return;
        }//先判断用户名、密码、邮箱、验证码是否为空

        let reEml = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
        if (reEml.test(this.state.email) === false) {
            this.alert('ERROR!', 'Email format is wrong!')
            return;
        } else if (this.state.password1 !== this.state.password2)//如果两次密码不一致
        {
            this.alert('ERROR!', 'Two passwords are not the same!')
            return;
        }//再判断两次密码是否一致、邮箱是否符合规范

        //验证验证码
        let formdata = new FormData();
        formdata.append('email', this.state.email);
        formdata.append('veriCode', this.state.code);
        let opts = {
            method: "POST",
            body: formdata,
        };
        fetch('http://34.203.103.12:8181/user/register/check', opts)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data === 101) {
                    this.alert('ERROR!', 'verification code is wrong!')
                } else {
                    //验证其他信息
                    let formdata1 = new FormData();
                    formdata1.append('username', this.state.username);
                    formdata1.append('password', this.state.password1);
                    formdata1.append('email', this.state.email);
                    let opts1 = {
                        method: "POST",
                        body: formdata1,
                    };

                    fetch('http://34.203.103.12:8181/user/register', opts1)
                        .then((response) => {
                            return response.json();
                        })
                        .then((data) => {
                            this.checkregister(data);
                        })
                }
            })
    };

    checkregister = (data) => {
        if (data.code === 111)//username&email重复
            this.alert('REGISTER FAIL!', 'Username and email already exist!')
        else if (data.code === 110)//username重复
            this.alert('REGISTER FAIL!', 'Username already exists!')
        else if (data.code === 101)//email重复
            this.alert('REGISTER FAIL!', 'Email already exists!')
        else {
            // sessionStorage.setItem('user', data.id);
            // sessionStorage.setItem('isAdmin', '0');
            // sessionStorage.setItem('token', data.data);
            this.alert('SUCCESS!', '');
            this.props.history.replace({pathname: '/'});
        }
    };

    alert = (mess, des) => {
        notification.open({
            message: mess,
            description: des,
        });
        clearTimeout(2000);
    };

    render() {
        return (
            <div className="signUp-page">
                <div className="signUp-container">
                    <div className="signUp-box">
                        <h1 className="page-title">REGISTER</h1>
                        <div className="signUp-content">

                            <Input size="large" placeholder="username" prefix={<UserOutlined/>}
                                   onChange={this.userChange}/>
                            <br/>
                            <Input.Password size="large" placeholder="password" prefix={<LockOutlined/>}
                                            onChange={this.password1Change}/>
                            <br/>
                            <Input.Password size="large" placeholder="password again" prefix={<LockOutlined/>}
                                            onChange={this.password2Change}/>
                            <br/>
                            <Input size="large" placeholder="email" prefix={<MailOutlined/>}
                                   onChange={this.emailChange}/>
                            <br/>
                            <div>
                                <Input size="large" style={{width: 210}} placeholder="input verification code"
                                       prefix={<UserOutlined/>} onChange={this.codeChange}/>
                                <Button shape="round" size="large" class="signUp-btn"
                                        onClick={this.handlemailtest}>发送验证码</Button>
                            </div>
                            <br/>
                            <br/>
                            <Button shape="round" size="large" onClick={this.handleCancel}>cancel</Button>
                            <Button type="primary" shape="round" size="large" style={{float: "right"}}
                                    onClick={this.handleRegister}>confirm</Button>

                        </div>
                    </div>
                </div>

            </div>
        );


    }
}

export default Register;