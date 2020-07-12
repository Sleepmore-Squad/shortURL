import React from 'react';
import { Layout, Menu } from 'antd';
const { Sider } = Layout;

class Sidermain extends React.Component {
    constructor(props) {
        super(props);
        this.handlehome = this.handlehome.bind(this);
        this.handleset = this.handleset.bind(this);
    }

    handlehome = () => {
        this.props.history.replace({pathname: '/home'});
    };

    handleset = () => {
        this.props.history.replace({pathname: '/Setting'});
    }

    render() {
        return (
            <Sider className="site-layout-background" width={200}>
                <Menu
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    style={{ height: '100%' }}
                >
                    <Menu.Item key="setting:1" text-align="center" onClick={this.handleHome}>Home</Menu.Item>
                    <Menu.Item key="setting:2" text-align="center" onClick={this.handleCart}>Set</Menu.Item>

                </Menu>
            </Sider>
        );
    }
}

export default Sidermain ;