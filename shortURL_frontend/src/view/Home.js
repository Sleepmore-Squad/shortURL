import React from 'react';
import '../css/home.css';
import Homemain from'../components/Homemain';
import  Sidermain from '../components/Sidermain';
import { Layout} from 'antd';
const {Content } = Layout;


class Home extends React.Component{
    componentWillMount() {
        if (sessionStorage.getItem('isAdmin') !== '1' && sessionStorage.getItem('isAdmin') !== '0')
            this.props.history.replace({pathname: '/'});
    }

    render() {
        return (
            <Content>
                <layout className="site-layout-background" style={{ padding: '24px 0' }}>
                    <div id="Contain">
                        <div id="Contain-side"><Sidermain history={this.props.history}/></div>
                        <div id="Contain-main"><Homemain history={this.props.history} /></div>
                    </div>

                </layout>

            </Content>
        )
    }


}

export default Home;