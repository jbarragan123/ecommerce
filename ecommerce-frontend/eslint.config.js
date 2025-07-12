import vue from 'eslint-plugin-vue';
import { FlatCompat } from '@eslint/eslintrc';

const compat = new FlatCompat();

export default [
  ...compat.extends('eslint:recommended'),
  {
    files: ['*.vue', '**/*.vue'],
    plugins: { vue },
    rules: {
      'vue/html-self-closing': 'error',
    },
  },
];
