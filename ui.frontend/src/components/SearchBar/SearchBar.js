import React, { useState, useEffect } from 'react';
import ProductCard from '../ProductCard/ProductCard';
import '../ProductCard/ProductCard.css';
import { MapTo } from '@adobe/aem-react-editable-components';
import './SearchBar.css';

const CustomSearchBarComponentConfig = {
    emptyLabel: "Search Bar Component",
    isEmpty: function (props) {
      return !props || !props.buttonText;
    },
};

const SearchBar = ({ buttonText = "Search" }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [products, setProducts] = useState([]);
    const [isTyping, setIsTyping] = useState(false);

    useEffect(() => {
        if (!searchTerm) {
            setProducts([]);
            return;
        }

        const timerId = setTimeout(() => {
            fetchProducts(searchTerm);
        }, 100); 

        return () => clearTimeout(timerId);
    }, [searchTerm]);

    const fetchProducts = async (query) => {
        try {
            const response = await fetch('http://localhost:4502/bin/store/product');
            const data = await response.json();

        
            const filteredProducts = data
                .filter(product => 
                    product.title.toLowerCase().includes(query.toLowerCase()) ||
                    product.category.toLowerCase().includes(query.toLowerCase())
                )
                .slice(0, 10); 

            setProducts(filteredProducts);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleSearchClick = () => {
        
        fetchProducts(searchTerm);
    };

    const handleChange = (e) => {
        setSearchTerm(e.target.value);
        setIsTyping(true);
    };

    return (
        <div className="search-bar">
            <input
                type="text"
                placeholder="Enter search term"
                value={searchTerm}
                onChange={handleChange}
            />
            <button onClick={handleSearchClick}>{buttonText}</button>
            <div className="product-results">
                    {products.map(product => (
                        <ProductCard
                            key={product.id}
                            title={product.title}
                            description={product.description}
                            imageUrl={product.image}
                            price={product.price}
                        />
                    ))}
            </div>
        </div>
    );
};

MapTo('ecommercereact/components/searchbar')(SearchBar, CustomSearchBarComponentConfig);

export default SearchBar;
