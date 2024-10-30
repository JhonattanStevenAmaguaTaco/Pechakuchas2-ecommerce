// SearchBar.js
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
            setProducts([]); // Limpiar resultados si no hay término de búsqueda
            return;
        }

        // Configurar un temporizador para la búsqueda
        const timerId = setTimeout(() => {
            fetchProducts(searchTerm);
        }, 150); // Esperar 500 ms después de que el usuario deje de escribir

        return () => clearTimeout(timerId); // Limpiar el temporizador si el usuario sigue escribiendo
    }, [searchTerm]);

    const fetchProducts = async (query) => {
        try {
            const response = await fetch('http://localhost:4502/bin/store/product');
            const data = await response.json();

            // Filtrar productos que coincidan con el término de búsqueda en título o categoría
            const filteredProducts = data
                .filter(product => 
                    product.title.toLowerCase().includes(query.toLowerCase()) ||
                    product.category.toLowerCase().includes(query.toLowerCase())
                )
                .slice(0, 6); // Limitar a los primeros 6 resultados

            setProducts(filteredProducts);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleSearchClick = () => {
        // Al hacer clic en el botón, forzamos una búsqueda con el término actual
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

// Mapear el componente a AEM
MapTo('ecommercereact/components/searchbar')(SearchBar, CustomSearchBarComponentConfig);

export default SearchBar;
