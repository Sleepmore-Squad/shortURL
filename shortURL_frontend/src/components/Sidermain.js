import React from 'react';
import { Layout, Menu } from 'antd';
const { Sider } = Layout;

class Sidermain extends React.Component {
    constructor(props) {
        super(props);
        this.handleHome = this.handleHome.bind(this);
    }

    handleHome = () => {
        this.props.history.replace({pathname: '/home'});
    };

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

                </Menu>
            </Sider>
        );
    }
}

export default Sidermain ;