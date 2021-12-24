const path = require('path');

module.exports = {
    //TODO: create prod config... eventually
    mode: 'development',
    entry: './src/index.tsx',
    output: {
        path: path.resolve(__dirname, '../resources/static/dist'),
        filename: 'bundle.js',
    },
    devtool: 'cheap-module-source-map',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
};