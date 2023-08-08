import { PostInformationHeader } from './PostInformationHeader';
import { PostInformationHeaderMeta } from './PostInformationHeaderMeta';

type Props = { title: string; id: number };

export const PostInformation: React.FC<Props> = ({ title, id }: Props) => {
  return (
    <>
      <div
        css={{
          width: '100%',
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
        }}
      >
        <PostInformationHeader title={title} id={id} />
        <PostInformationHeaderMeta />
      </div>
    </>
  );
};
