import styled from "styled-components";

interface ProfileImgProps {
  size: number;
  $url: string;
}

export const ProfileImg: React.FC<ProfileImgProps> = ({ size, $url }) => {
  return <Layout size={size} $url={$url} />;
};

const Layout = styled.div<ProfileImgProps>`
  width: ${(props) => props.size}px;
  height: ${(props) => props.size}px;
  border-radius: 50%;
  background-size: cover;
  background-position: center;
  background-image: url(${(props) => props.$url});
`;
