// CustomHeader.js
import React, { Component } from 'react';
import './CustomHeader.css';
import { MapTo } from '@adobe/aem-react-editable-components';

const CustomHeaderComponentConfig = {
    emptyLabel: "Header Component",
    isEmpty: function (props) {
        return !props || !props.title;
    },
};

class CustomHeader extends Component {
    render() {
        const { title, pageUrl, pageName, fileReference, backgroundColor = "#333" } = this.props;

        return (
            <header
                className="custom-header"
                style={{
                    backgroundImage: `url(${fileReference})`,
                    backgroundColor: backgroundColor,
                }}
            >
                <div className="header-overlay">
                    <h1 className="header-title">
                        {/* Enlace envuelto en el título */}
                        <a href={pageUrl} target="_blank" rel="noopener noreferrer">
                            {pageName}
                        </a>
                    </h1>
                </div>
            </header>
        );
    }
}

export default CustomHeader;
MapTo('ecommercereact/components/customheader')(CustomHeader, CustomHeaderComponentConfig);
