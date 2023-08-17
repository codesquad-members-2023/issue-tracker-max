import { Theme, css } from '@emotion/react';
import { Button } from './Button';
import { createPortal } from 'react-dom';

type Props = {
  action: 'normal' | 'danger';
  leftButtonText: string;
  rightButtonText: string;
  onClose: () => void;
  onConfirm: () => void;
  children: React.ReactNode;
};

export const Alert: React.FC<Props> = ({
  action,
  leftButtonText,
  rightButtonText,
  onClose,
  onConfirm,
  children,
}) => {
  return (
    <>
      {createPortal(
        <div css={(theme) => alertStyle(theme, action)}>
          <div className="dim" onClick={onClose} />
          <div className="modal">
            <div className="body">{children}</div>

            <div className="button-container">
              <Button
                className="left-button"
                typeVariant="outline"
                size="S"
                onClick={onClose}
              >
                {leftButtonText}
              </Button>
              <Button
                className="right-button"
                typeVariant="outline"
                size="S"
                onClick={onConfirm}
              >
                {rightButtonText}
              </Button>
            </div>
          </div>
        </div>,
        document.body,
      )}
    </>
  );
};

const alertStyle = (theme: Theme, action: 'normal' | 'danger') => css`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .dim {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 9998;
    opacity: ${theme.opacity.disabled};
    background-color: #696969;
  }

  .modal {
    position: relative;
    z-index: 9999;
    width: 359px;
    padding: 32px;
    border: ${`${theme.border.default} ${theme.neutral.border.default} `};
    border-radius: ${theme.radius.l};
    background-color: ${theme.neutral.surface.strong};
    display: flex;
    flex-direction: column;
    gap: 24px;
  }

  .body {
    min-height: 52px;
  }

  .button-container {
    display: flex;
    gap: 8px;

    .left-button {
      width: 100%;
      border-radius: ${theme.radius.l};

      ${action === 'normal'
        ? css`
            border-color: ${theme.brand.border.default};
            color: ${theme.brand.text.weak};
          `
        : css`
            border-color: ${theme.danger.border.default};
            color: ${theme.danger.text.default};
          `}
    }

    .right-button {
      width: 100%;
      border-radius: ${theme.radius.l};
      color: ${theme.brand.text.default};

      ${action === 'normal'
        ? css`
            border-color: ${theme.brand.surface.default};
            background-color: ${theme.brand.surface.default};
          `
        : css`
            border-color: ${theme.danger.border.default};
            background-color: ${theme.danger.border.default};
          `}
    }
  }
`;
