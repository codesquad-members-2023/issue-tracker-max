import { Theme, css } from '@emotion/react';
import { ReactComponent as UserImageLarge } from '@assets/icons/userImageLarge.svg';

type Props = {
  image?: string;
};

export const UserImage: React.FC<Props> = ({ image }) => {
  return (
    <>
      {image && (
        <div css={UserImageStyle}>
          <UserImageLarge className="user-image-icon" />
          <img className="user-image" alt="user-image" src={image} />
        </div>
      )}
    </>
  );
};

const UserImageStyle = (theme: Theme) => css`
  position: relative;

  .user-image-icon {
    fill: ${theme.neutral.surface.bold};
  }

  .user-image {
    width: 32px;
    height: 32px;
    position: absolute;
    border-radius: ${theme.radius.half};
    top: 0;
    left: 0;
  }
`;
