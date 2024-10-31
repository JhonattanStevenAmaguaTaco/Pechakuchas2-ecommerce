import React, { Component } from 'react';
import './ProductCard.css';
import { MapTo } from '@adobe/aem-react-editable-components';

const CustomProductCardComponentConfig = {
    emptyLabel: "Product Card Component",
    isEmpty: function (props) {
      return !props || !props.title;
    },
};

class ProductCard extends Component {
    render() {
        const { title, description, imageUrl, price, buttonText = "SALE", labelSale  = "SALE", isUsingTheSameString } = this.props;

        return (
            <div className="ProductCard">
                <div className="image-container">
                    {labelSale && <div className="sale-label">{labelSale}</div>}
                    
                    {imageUrl ? (
                        <img src={imageUrl} alt="Product Image" />
                    ) : (
                        <img src="https://th.bing.com/th/id/OIP.AC9frN1qFnn-I2JCycN8fwHaEK?rs=1&pid=ImgDetMain" alt="Image Not Found" />
                    )}
                    <button className="shop-button">{buttonText}</button>
                </div>
                <div className="product-details">
                    <div className="product-title">{title}</div>
                    <div className="product-description">{description}</div>
                    <div className="product-price">{price ? `$${price}` : "Price not available"}</div>
                    <div className="hash-code">{isUsingTheSameString}</div>
                </div>
            </div>
        );
    }
}

export default ProductCard;
MapTo('ecommercereact/components/productcard')(ProductCard, CustomProductCardComponentConfig);
