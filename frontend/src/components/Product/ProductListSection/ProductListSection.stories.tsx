import { ComponentStory } from '@storybook/react';

import ProductListSection from '@/components/Product/ProductListSection/ProductListSection';

import { products } from '@/mocks/data';

export default {
  component: ProductListSection,
  title: 'Section/ProductListSection',
};

const Template: ComponentStory<typeof ProductListSection> = (args) => (
  <ProductListSection {...args} />
);

export const Default = () => (
  <Template data={products} isLoading={false} isError={false} />
);
