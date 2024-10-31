// CodeViewer.js
import React, { Component } from 'react';
import './CodeViewer.css';

import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { nightOwl } from 'react-syntax-highlighter/dist/esm/styles/prism'; // Cambiamos a githubDark
import { MapTo } from '@adobe/aem-react-editable-components';

const CustomCodeViewerComponentConfig = {
    emptyLabel: "Code Viewer Component",
    isEmpty: function (props) {
        return !props || !props.codeText;
    },
};

class CodeViewer extends Component {
    render() {
        const { codeText, language = 'java' } = this.props;

        return (
            <div className="code-viewer">
                <SyntaxHighlighter language={language} style={nightOwl}>
                    {codeText}
                </SyntaxHighlighter>
            </div>
        );
    }
}

export default CodeViewer;
MapTo('ecommercereact/components/codeviewer')(CodeViewer, CustomCodeViewerComponentConfig);
