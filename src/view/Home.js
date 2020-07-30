import React from 'react';
import 'antd/dist/antd.css';
import '../css/home.css';
import Homemain from '../components/Homemain';
import Sidermain from '../components/Sidermain';
import { Layout } from 'antd';
const { Content } = Layout;


class Home extends React.Component
{
    componentWillMount() {
        if (sessionStorage.getItem('isAdmin') !== '1' && sessionStorage.getItem('isAdmin') !== '0')
            this.props.history.replace({pathname: '/'});
    }

    render()
    {
        return (
            <Content>
                <layout className="site-layout-background" style={{ padding: '24px 0' }}>
                    <div className="Contain">
                        <div className="Contain-side"><Sidermain histort={this.props.history} /></div>
                        <div className="Contain-main"><Homemain history={this.props.history} /></div>
                    </div>

                </layout>

            </Content>
        )
    }


}

export default Home;
