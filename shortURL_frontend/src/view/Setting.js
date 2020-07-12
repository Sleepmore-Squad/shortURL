import React from 'react';
import 'antd/dist/antd.css';
import '../css/home.css';
import  Settingmain from '../components/Settingmain';
import  Sidermain from '../components/Sidermain';
import { Layout} from 'antd';
const {Content } = Layout;


class Setting extends React.Component{
    componentWillMount() {
        if (sessionStorage.getItem('isAdmin') !== '1' && sessionStorage.getItem('isAdmin') !== '0')
            this.props.history.replace({pathname: '/'});
    }

    render() {
        return (
            <Content>
                <layout className="site-layout-background" style={{ padding: '24px 0' }}>
                    <div id="Contain">
                        <div id="Contain-side"><Sidermain histort={this.props.history}/></div>
                        <div id="Contain-main"><Settingmain history={this.props.history} /></div>
                    </div>

                </layout>

            </Content>
        )
    }

}

export default Setting;