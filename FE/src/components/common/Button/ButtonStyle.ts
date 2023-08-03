import { css } from "styled-components";

export const CommonStyle = css`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  &:hover {
    opacity: ${({ theme }) => theme.opacity.hover};
  }
  &:active {
    opacity: ${({ theme }) => theme.opacity.press};
  }
  &:disabled {
    opacity: ${({ theme }) => theme.opacity.disabled};
  }
`;

export const SizeStyles = {
  large: css`
    width: 240px;
    height: 56px;
    font: ${({ theme }) => theme.font.availableMedium20};
    border-radius: ${({ theme }) => theme.radius.large};
  `,
  medium: css`
    width: 184px;
    height: 48px;
    font: ${({ theme }) => theme.font.availableMedium16};
    border-radius: ${({ theme }) => theme.radius.medium};
  `,
  small: css`
    width: 128px;
    height: 40px;
    font: ${({ theme }) => theme.font.availableMedium12};
    border-radius: ${({ theme }) => theme.radius.medium};
  `,
};

export const TypeStyles = {
  container: css`
    color: ${({ theme }) => theme.colorSystem.brand.text.default};
    background-color: ${({ theme }) => theme.colorSystem.brand.surface.default};
  `,
  outline: css`
    color: ${({ theme }) => theme.colorSystem.brand.text.weak};
    background-color: transparent;
    border: ${({ theme }) =>
      `${theme.border.default} ${theme.colorSystem.brand.text.weak}`};
  `,
  ghost: css`
    width: auto;
    height: auto;
    color: ${({ theme }) => theme.colorSystem.neutral.text.default};
    background-color: transparent;
  `,
};

export const LargeGapStyle = css`
  gap: 8px;
`;

export const IconSizeStyles = {
  large: css`
    width: 24px;
    height: 24px;
  `,
  medium: css`
    width: 16px;
    height: 16px;
  `,
  small: css`
    width: 12px;
    height: 12px;
  `,
};

export const IconColorStyles = {
  container: css`
    filter: ${({ theme }) => theme.filter.brand.text.default};
  `,
  outline: css`
    filter: ${({ theme }) => theme.filter.brand.text.weak};
  `,
  ghost: css`
    filter: ${({ theme }) => theme.filter.neutral.text.default};
  `,
};
